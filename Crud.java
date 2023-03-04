// ===================== IMPORTING LIBRARIES ===========================

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.ArrayList;

public class Crud {

    // declaration of variables
    public int lastId;
    public int fileLength;
    public ArrayList<Airbnb> hostel;
    String filename;

    //empty contructor
    public Crud() {
        this.lastId = 0;
        this.fileLength = 0;
        this.hostel = new ArrayList<>();
        this.filename = "out.bin";
    }

    // =================================== LOAD FILE IN BINARY METHOD ==================================
    // This method get all the data in csv convert to binary and write in the binary file
    public void loadFile() throws ParseException, IOException{
        
        BufferedReader reader = null;
        byte[] bytesdata;
        String file = "Airbnb.csv";
        String readline;
        

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        

        try{
            reader = new BufferedReader(new FileReader(file));

            while((readline = reader.readLine()) != null) {
                //adding the data in a list to make it easy to manipulate
                hostel.add(fileLength, new Airbnb());
                hostel.get(fileLength).read(readline);

                bytesdata = hostel.get(fileLength).toByteArray();
                //write the size of the record before it
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

    //================================ SEARCH BY ID METHOD ===========================
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

    //================================ DELETE BY ID METHOD ===========================
    public boolean delete(int id) throws IOException{

        try (RandomAccessFile filebytes = new RandomAccessFile(filename, "rw")) {

            //moving the pointer to the beginning of the file
            int pos = 0;
            filebytes.seek(pos);

           int size;

            // read length of record, check isValid and check id
            // if id is false, move pointer to the next record
            // if id matches, change isvalid to false
            while((size = filebytes.readInt()) != -1){
                pos += 4; 
                if(filebytes.readBoolean() == true){
                    if(filebytes.readInt() == id){
                        filebytes.seek(pos);
                        filebytes.writeBoolean(false);
                        // rec(0, id);
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

    //recursive method to delete the record in the list
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

    //================================ CREATE A NEW AIRBNB ===========================
    public Airbnb create() throws ParseException, IOException{

        byte[] bytesdata;
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        
        //move the pointer to the end of the file
        filebytes.seek(filebytes.length());

        //generate the next id
        int id = lastId + 1;
        
        System.out.println("ADDING NEW AIRBNB");
        Airbnb newHostel = Main.scan(id);

        //addind in the list
        hostel.add(newHostel);

        bytesdata = newHostel.toByteArray();
        //write the size of the record before it
        filebytes.writeInt(bytesdata.length);
        filebytes.write(bytesdata);

        lastId = newHostel.id;
        fileLength++;
        
        filebytes.close();
        
        return null;
    }

    //================================ CREATE A NEW AIRBNB ================================
    public Airbnb update(int id) throws IOException, ParseException{

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        int size;
        byte[] bytesdata;
        int pos = 0;

        System.out.println("UPDATING AIRBNB ID" +id);
        Airbnb newHostel = Main.scan(id);
        bytesdata = newHostel.toByteArray();
        
        // read length of record, check isValid and check id
        // if id is false, move pointer to the next record
        // if the id matches and the new record is smaller or the same size, just rewrite it
        //if the id matches and the new record is bigger, delete the old one and create the new record int he end of the file
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
                        delete(id);
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

