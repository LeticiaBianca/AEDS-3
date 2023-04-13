// ===================== IMPORTING LIBRARIES ===========================

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

public class Crud {

    // declaration of variables
    public int lastId;
    public String filename;
    public Btree iBtree;
    public Hashing iHash;

    //empty contructor
    public Crud() throws FileNotFoundException {
        this.lastId = 0;
        this.filename = "out.bin";
        this.iBtree = new Btree();
        this.iHash = new Hashing(1);
    }

    // =================================== LOAD FILE IN BINARY METHOD ==================================
    // This method get all the data in csv convert to binary and write in the binary file
    public void loadFile() throws ParseException, IOException{
        
        BufferedReader reader = null;
        byte[] bytesdata;
        String file = "Airbnb.csv";
        String readline;

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        int pos = 0;

        try{
            reader = new BufferedReader(new FileReader(file));

            while((readline = reader.readLine()) != null) {
                Airbnb aux = new Airbnb();
                aux.read(readline);
                bytesdata = aux.toByteArray();
                //write the size of the record before it
                filebytes.writeInt(bytesdata.length);
                filebytes.write(bytesdata);
                lastId = aux.id;

                //Key for index files
                pos += 4;
                Key k = new Key(aux.id, pos);
                
                //BTREE
                iBtree.insert(k);
                //BTREE

                //HASHING
                iHash.insert(k);
                //HASHING

                pos += bytesdata.length;

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
        iBtree.printFile();
        iHash.printFile();
        filebytes.close();
    }

    //================================ SEARCH BY ID METHOD ===========================
    public Airbnb searchId(int id) throws Exception{

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        Airbnb aux = null;

         //moving the pointer to the beginning of the file
         int pos = 0;

         // read the length of record, check isValid and check id
         // if id is false, move pointer to the next record
         // if id matches, crate a object with this record
        while(filebytes.getFilePointer() < filebytes.length()){
            filebytes.seek(pos);
            int size = filebytes.readInt();
            pos += 4; 
            if(filebytes.readBoolean() == true){
                if(filebytes.readInt() == id){
                    filebytes.seek(pos);
                    aux = new Airbnb();
                    aux.fromByteArray(pos-4, "out.bin");
                    break;
                }else{
                    pos += size;
                }
            }else{
                pos += size;
            }
        }
        if(aux == null){
            System.out.println("ID NOT FOUND!");
        }

        filebytes.close();
        return aux;
    }

    //================================ DELETE BY ID METHOD ===========================
    public boolean delete(int id) throws IOException{

        try (RandomAccessFile filebytes = new RandomAccessFile(filename, "rw")) {

            //moving the pointer to the beginning of the file
            int pos = 0;
            filebytes.seek(pos);

            // read length of record, check isValid and check id
            // if id is false, move pointer to the next record
            // if id matches, change isvalid to false
            while(filebytes.getFilePointer() < filebytes.length()){
                int size = filebytes.readInt();
                pos += 4; 
                if(filebytes.readBoolean() == true){
                    if(filebytes.readInt() == id){
                        filebytes.seek(pos);
                        filebytes.writeBoolean(false);
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


    //================================ CREATE A NEW AIRBNB ===========================
    public Airbnb create() throws ParseException, IOException{

        byte[] bytesdata;
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        
        //move the pointer to the end of the file
        int len = (int) filebytes.length();
        filebytes.seek(filebytes.length());

        //generate the next id
        int id = lastId + 1;
        
        System.out.println("ADDING NEW AIRBNB");
        Airbnb newHostel = Main.scan(id);

        bytesdata = newHostel.toByteArray();
        //write the size of the record before it
        filebytes.writeInt(bytesdata.length);
        filebytes.write(bytesdata);

        lastId = newHostel.id;

        //Key for index files
        Key k = new Key(newHostel.id, len+4);
        
        //BTREE
        iBtree.insert(k);
        iBtree.printFile();
        //BTREE

        //HASHING
        iHash.insert(k);
        iHash.printFile();
        //HASHING       
        
        filebytes.close();
        
        return null;
    }

    //================================ UPDATE A AIRBNB ================================
    public Airbnb update(int id) throws IOException, ParseException{

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");
        byte[] bytesdata;
        int pos = 0;

        System.out.println();
        System.out.println("UPDATING AIRBNB ID " +id);
        Airbnb newHostel = Main.scan(id);
        bytesdata = newHostel.toByteArray();
        
        // read length of record, check isValid and check id
        // if id is false, move pointer to the next record
        // if the id matches and the new record is smaller or the same size, just rewrite it
        //if the id matches and the new record is bigger, delete the old one and create the new record int he end of the file
        while(filebytes.getFilePointer() < filebytes.length()){ 
            int size = filebytes.readInt();
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
                        int len  = (int) filebytes.length();
                        filebytes.seek(filebytes.length());
                        filebytes.writeInt(bytesdata.length);
                        filebytes.write(bytesdata);
                        
                        //Key for index files
                        Key k = new Key(newHostel.id, len+4);
        
                        //BTREE
                        iBtree.insert(k);
                        iBtree.printFile();
                        //BTREE

                        //HASHING
                        iHash.insert(k);
                        iHash.printFile();
                        //HASHING       
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
    
    public Airbnb getByPos(int pos) throws IOException{
        try (RandomAccessFile filebytes = new RandomAccessFile(filename, "rw")) {
            Airbnb aux = null;
            filebytes.seek(pos);
            aux = new Airbnb();
            aux.fromByteArray(pos-4, "out.bin");
            return aux;
        }
    }
}

