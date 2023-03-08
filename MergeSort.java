import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MergeSort {

    public int blocksize;
    public String filename;
    

    public MergeSort() throws FileNotFoundException {
        this.blocksize = 1000;
        this.filename = "out.bin";
        RandomAccessFile temp1 = new RandomAccessFile("TempFile1", "rw");
        RandomAccessFile temp2 = new RandomAccessFile("TempFile2", "rw");
        RandomAccessFile temp3 = new RandomAccessFile("TempFile3", "rw");
        RandomAccessFile temp4 = new RandomAccessFile("TempFile4", "rw");
    }
    
    public void sort() throws IOException{
        int pos = 0;
        int i = 0;
        ArrayList<Airbnb> records = new ArrayList<>();

        RandomAccessFile filebytes = new RandomAccessFile(filename, "r");

        while (filebytes.getFilePointer() != filebytes.length()) {
            while(filebytes.getFilePointer() != filebytes.length() || i < blocksize){
                records.add(new Airbnb());
                records.get(i).fromByteArray(pos, filename);
                int size = filebytes.readInt();
                pos += 4; 
                pos += size;
                i++;
                filebytes.seek(pos);
            }
            records = sortList(records);
        }
        
        filebytes.close();
        
    }

    public ArrayList<Airbnb> sortList(ArrayList<Airbnb> records) {

        

        return records;
    }
}
