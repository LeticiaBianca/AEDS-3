// ===================== IMPORTING LIBRARIES ===========================

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MergeSort {

    // declaration of variables
    public int blocksize;
    public String filename;
    RandomAccessFile temp1;
    RandomAccessFile temp2;
    RandomAccessFile temp3;
    RandomAccessFile temp4;
    
    //empty contructor
    public MergeSort() throws FileNotFoundException {
        this.blocksize = 1000;
        this.filename = "out.bin";
        this.temp1 = new RandomAccessFile("TempFile1.bin", "rw");
        this.temp2 = new RandomAccessFile("TempFile2.bin", "rw");
        this.temp3 = new RandomAccessFile("TempFile3.bin", "rw");
        this.temp4 = new RandomAccessFile("TempFile4.bin", "rw");
    }

    //This method sort the out.bin file and create a new file
    public void sort() throws IOException{
        int pos = 0;
        //this boolean define if the block will be in the block
        boolean first = false;

        ArrayList<Airbnb> records = new ArrayList<>();
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

        //split the file, sort the block and write it on a temporary file
        while (filebytes.getFilePointer() < filebytes.length()) {
            first = !first;
            int i = 0;
            while(filebytes.getFilePointer() < filebytes.length() && i < blocksize){
                records.add(new Airbnb());
                records.get(i).fromByteArray(pos, filename);
                filebytes.seek(pos);
                int size = filebytes.readInt();
                pos += 4; 
                pos += size;
                i++;
                filebytes.seek(pos);
            }

            records = quicksort(records, 0, records.size()-1);

            for (Airbnb record : records) {
                if(first == true){
                    byte[] bytesdata = record.toByteArray();
                    temp1.writeInt(bytesdata.length);
                    temp1.write(bytesdata);
                }else{
                    byte[] bytesdata = record.toByteArray();
                    temp2.writeInt(bytesdata.length);
                    temp2.write(bytesdata);
                }
            }            
            records.clear();
        }

        //intercalate the blocks until has one left
        intercalate(temp1, temp2, "TempFile1.bin", "TempFile2.bin", temp3, temp4, 1000);

        //delete the files
        File f2 = new File("TempFile2.bin");
        f2.delete();

        File f3 = new File("TempFile3.bin");
        f3.delete();

        File f4 = new File("TempFile4.bin");
        f4.delete();

        
        filebytes.close();    
    }

    //Quicksort method
    public ArrayList<Airbnb> quicksort(ArrayList<Airbnb> records, int start, int end) {

        if (start < end) {
            int aux = separate(records, start, end);
            quicksort(records, start, aux - 1);
            quicksort(records, aux + 1, end);
        }

        return records;
    }

    public int separate(ArrayList<Airbnb> records, int start, int end) {
        Airbnb pos = records.get(start);
        int i = start + 1, j = end;

        while(i <= j){
            if (records.get(i).id <= pos.id)
                i++;
            else if (pos.id < records.get(j).id)
                j--;
            else {
                Airbnb change = new Airbnb();
                change = records.get(i);
                records.set(i, records.get(j));
                records.set(j, change);
                i++;
               j--;
            }
        }

        records.set(start, records.get(j));
        records.set(j, pos);
        return j;
    }
     //intercalate the blocks until has one left
    public void intercalate(RandomAccessFile temp01, RandomAccessFile temp02, String name1, String name2, RandomAccessFile temp03, RandomAccessFile temp04, int blocks) throws IOException {
       //this boolean define if the block will be in the block
        boolean first = false;
        int pos1 = 0, pos2 = 0;
        Airbnb record1 = new Airbnb(), record2 = new Airbnb();

        try {
            temp01.seek(pos1);
            temp02.seek(pos2);
        
            while(temp01.getFilePointer() < temp01.length() && temp02.getFilePointer() < temp02.length()){  
                first = !first;
                int i=0, j=0;

                //intercalate until one of tthe files hit the end
                while(temp01.getFilePointer() < temp01.length() && temp02.getFilePointer() < temp02.length() && i < blocks && j < blocks){
                    temp01.seek(pos1);
                    temp02.seek(pos2);
                
                    record1.fromByteArray(pos1, name1);
                    record2.fromByteArray(pos2, name2);

                    if(record1.id < record2.id){
                        if(first == true){
                            byte[] bytesdata = record1.toByteArray();
                            temp03.writeInt(bytesdata.length);
                            temp03.write(bytesdata);
                            pos1 = pos1 + 4 + temp01.readInt();
                            i++;
                        }
                        else{
                            byte[] bytesdata = record1.toByteArray();
                            temp04.writeInt(bytesdata.length);
                            temp04.write(bytesdata);
                            pos1 = pos1 + 4 + temp01.readInt();
                            i++;
                        }
                    }
                    else{
                        if(first == true){
                            byte[] bytesdata = record2.toByteArray();
                            temp03.writeInt(bytesdata.length);
                            temp03.write(bytesdata);
                            pos2 = pos2 + 4 + temp02.readInt();
                            j++;
                        }
                        else{
                            byte[] bytesdata = record2.toByteArray();
                            temp04.writeInt(bytesdata.length);
                            temp04.write(bytesdata);
                            pos2 = pos2 + 4 + temp02.readInt();
                            j++;
                        }
                    }
                }

                // continue with the other file
                if(i < blocks){
                    
                    while(temp01.getFilePointer() < temp01.length() && i<blocks){
                        temp01.seek(pos1);            
                        record1.fromByteArray(pos1, name1);

                        if(first == true){
                            byte[] bytesdata = record1.toByteArray();
                            temp03.writeInt(bytesdata.length);
                            temp03.write(bytesdata);
                            pos1 = pos1 + 4 + temp02.readInt();
                        }
                        else{
                            byte[] bytesdata = record1.toByteArray();
                            temp04.writeInt(bytesdata.length);
                            temp04.write(bytesdata);
                            pos1 = pos1 + 4 + temp02.readInt();
                        }
                        i++;
                    }
                } else if(j < blocks){
                    
                    while(temp02.getFilePointer() < temp02.length() && j<blocks){

                        temp02.seek(pos2);
                        record2.fromByteArray(pos2, name2);

                        if(first == true){
                            byte[] bytesdata = record2.toByteArray();
                            temp03.writeInt(bytesdata.length);
                            temp03.write(bytesdata);
                            pos2 = pos2 + 4 + temp02.readInt();
                        }
                        else{
                            byte[] bytesdata = record2.toByteArray();
                            temp04.writeInt(bytesdata.length);
                            temp04.write(bytesdata);
                            pos2 = pos2 + 4 + temp02.readInt();
                        }
                        j++;
                    }
                }   
            }
            //delete the content of the files
            temp01.setLength(0);
            temp02.setLength(0);

            //see if the intercalation is done
            if(temp04.length() > 0){
                if(name1.compareTo("TempFile1.bin") == 0){
                    intercalate(temp3, temp4, "TempFile3.bin", "TempFile4.bin", temp1, temp2, blocks*2);
                }
                else{
                    intercalate(temp1, temp2, "TempFile1.bin", "TempFile2.bin", temp3, temp4, blocks*2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close the files
            temp01.close();
            temp02.close();
            temp03.close();
            temp04.close();
           
        }
    }

}
