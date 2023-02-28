import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;

public class Crud {
    public int lastId;
    public int fileLength;
    public ArrayList<Airbnb> hostel;

    public Crud() {
        this.lastId = 0;
        this.fileLength = 0;
        this.hostel = new ArrayList<>();
    }

    public void loadFile() throws FileNotFoundException, ParseException{
        ArrayList<String> line = new ArrayList<>();
        String filename = "out.bin";
        BufferedReader reader = null;
        byte[] bytesdata;
        String file = "Airbnb.csv";
        String readline;

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

        try{
            reader = new BufferedReader(new FileReader(file));
            while((readline = reader.readLine()) != null) {
                line.add(readline);
                hostel.add(fileLength, new Airbnb());
                hostel.get(fileLength).read(line.get(fileLength));
                bytesdata = hostel.get(fileLength).toByteArray();
                filebytes.writeInt(bytesdata.length);
                filebytes.write(bytesdata);
                lastId = hostel.get(fileLength).id;
                fileLength++;
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
    public void negativeException(int id) throws Exception{
        if(id < 0){
            throw new Exception("INVALID ID!!!");
        }
        
    }

    public Airbnb searchId(int id) throws Exception{
        Airbnb maybe = new Airbnb();
        for(int i = 0; i < fileLength; i++){
            if(hostel.get(i).id == id){
                maybe = hostel.get(i);
            }
            else {
                System.out.println("UNKONW ID!");
                maybe = null;
            }            
        }
        return maybe;
    }
}

