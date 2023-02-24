import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static boolean isFim(String s){
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main(String[] args) {
        String file = "Airbnb.csv";
        BufferedReader reader = null;
        String line[] = new String[80000];
        int i = 0;

        try{
            reader = new BufferedReader(new FileReader(file));
            while((line[i] = reader.readLine()) != null) {
                Airbnb acomodations = new Airbnb();
                acomodations.read(file);
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