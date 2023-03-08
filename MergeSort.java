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
        System.out.println("buuu");
        int pos = 0;
        int i = 0;
        ArrayList<Airbnb> records = new ArrayList<>();

        RandomAccessFile filebytes = new RandomAccessFile(filename, "r");

        while (filebytes.getFilePointer() != filebytes.length()) {
            System.out.println("i am here");
            while(filebytes.getFilePointer() != filebytes.length() || i < blocksize){
                System.out.println("fuck u bitch");
                records.add(new Airbnb());
                records.get(i).fromByteArray(pos, filename);
                int size = filebytes.readInt();
                pos += 4; 
                pos += size;
                i++;
                filebytes.seek(pos);
            }
            System.out.println(records);
            records = quicksort(records, 0, records.size()-1);
            System.out.println(records);
            records.clear();
        }
        
        filebytes.close();
        
    }

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
            if (records.get(i).rating <= pos.rating)
                i++;
            else if (pos.rating < records.get(j).rating)
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
}
