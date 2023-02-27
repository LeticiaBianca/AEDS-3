import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class Main {

    public static boolean isFim(String s){
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String file = "Airbnb.csv";
        BufferedReader reader = null;
        String line[] = new String[80000];
        int i = 0;
        String filename = "out.bin";
        byte[] bytesdata;

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

        try{
            reader = new BufferedReader(new FileReader(file));
            while((line[i] = reader.readLine()) != null) {
                Airbnb acomodations = new Airbnb();
                acomodations.read(line[i]);
                bytesdata = acomodations.toByteArray();
                filebytes.writeInt(bytesdata.length);
                filebytes.write(bytesdata);
                //passar para arquivo: função na classe Airbnb
                i++;
            }
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally{
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}