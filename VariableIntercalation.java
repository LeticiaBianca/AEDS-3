import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class VariableIntercalation {
    public int blocksize;
    public String filename;
    RandomAccessFile temp1;
    RandomAccessFile temp2;
    RandomAccessFile temp3;
    RandomAccessFile temp4;
    

    public VariableIntercalation() throws FileNotFoundException {
        this.blocksize = 5000;
        this.filename = "out.bin";
        this.temp1 = new RandomAccessFile("TempFile1.bin", "rw");
        this.temp2 = new RandomAccessFile("TempFile2.bin", "rw");
        this.temp3 = new RandomAccessFile("TempFile3.bin", "rw");
        this.temp4 = new RandomAccessFile("TempFile4.bin", "rw");
    }
    
    public void sort() throws IOException{
        int pos = 0;
        boolean first = false;
        ArrayList<Airbnb> records = new ArrayList<>();

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

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

        intercalate(temp1, temp2, "TempFile1.bin", "TempFile2.bin", temp3, temp4);
        
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
    
    private void intercalate(RandomAccessFile temp01, RandomAccessFile temp02, String name1, String name2, RandomAccessFile temp03, RandomAccessFile temp04) {
    }
    
}
