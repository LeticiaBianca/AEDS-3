import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;

public class Crud {
    public int lastId;
    public int fileLength;
    public ArrayList<Airbnb> hostel;
    String filename;

    public Crud() {
        this.lastId = 0;
        this.fileLength = 0;
        this.hostel = new ArrayList<>();
        this.filename = "out.bin";
    }

    public void loadFile() throws ParseException, IOException{
        BufferedReader reader = null;
        byte[] bytesdata;
        String file = "Airbnb.csv";
        String readline;
        

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        

        try{
            reader = new BufferedReader(new FileReader(file));
            while((readline = reader.readLine()) != null) {
                hostel.add(fileLength, new Airbnb());
                hostel.get(fileLength).read(readline);
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
        filebytes.close();
    }

    public void negativeException(int id) throws Exception{
        if(id < 0){
            throw new Exception("INVALID ID!!!");
        }
        
    }

    // search id
    public Airbnb searchId(int id) throws Exception{
        Airbnb aux = new Airbnb();
        for(int i = 0; i < fileLength; i++){
            if(hostel.get(i).id == id){
                aux = hostel.get(i);
                i = fileLength;
            }
            else{
                aux = null;
            }            
        }
        if(aux == null){
            System.out.println("ID NOT FOUND!");
        }
        return aux;
    }

    public boolean delete(int id) throws IOException{
        try (RandomAccessFile filebytes = new RandomAccessFile(filename, "rw")) {
            int pos = 0;
            filebytes.seek(pos);

            int size;

            while((size = filebytes.readInt()) != -1){
                pos += 4; 
                if(filebytes.readBoolean() == true){
                    if(filebytes.readInt() == id){
                        filebytes.seek(pos);
                        filebytes.writeBoolean(false);
                        rec(0, id);
                        return true;
                    }else{
                        pos += size;
                    }
                }else{
                    pos += size;
                }
                filebytes.seek(pos);
            }

            filebytes.close();
        }
        return false;
    }

    public void rec(int i, int id){
        if(i<fileLength){
            if(hostel.get(i).id == id){
                Airbnb aux = new Airbnb();
                aux = hostel.get(i);
                aux.isValid = false;
                hostel.set(i, aux);
                i = fileLength;
            }
            rec(i++, id);
        }
    }

    public Airbnb create() throws ParseException, IOException{
        byte[] bytesdata;
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        
        filebytes.seek(filebytes.length());

        int id = lastId + 1;

        Airbnb newHostel = Main.scan(id);

        hostel.add(newHostel);

        bytesdata = newHostel.toByteArray();
        filebytes.writeInt(bytesdata.length);
        filebytes.write(bytesdata);
        lastId = newHostel.id;
        fileLength++;
        lastId++;
        filebytes.close();
        
        return null;
    }

    public Airbnb update(int id) throws IOException, ParseException{
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        int size;
        byte[] bytesdata;
        int pos = 0;

        Airbnb newHostel = Main.scan(id);
        bytesdata = newHostel.toByteArray();
        
        
        while((size = filebytes.readInt()) != -1){ 
            if(filebytes.readBoolean() == true){
                if(filebytes.readInt() == id){
                    if(size >= bytesdata.length){
                        filebytes.seek(pos);
                        filebytes.writeInt(bytesdata.length);
                        filebytes.write(bytesdata);
                        break;
                    }
                    else{
                        filebytes.seek(pos + 4);
                        filebytes.writeBoolean(false);
                        
                        filebytes.seek(filebytes.length());
                        filebytes.writeInt(bytesdata.length);
                        filebytes.write(bytesdata);
                    }
                }else{
                    pos  = pos + size + 4;
                    
                }
            }else{
                pos  = pos + size + 4;
            }
            filebytes.seek(pos);
        }

        filebytes.close();
        return null;
    }
}

