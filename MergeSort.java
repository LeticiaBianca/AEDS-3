import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class MergeSort {

    public int blocksize;
    public String filename;
    

    public MergeSort() throws FileNotFoundException {
        this.blocksize = 100;
        this.filename = "out.bin";
        RandomAccessFile temp1 = new RandomAccessFile("TempFile1", "rw");
        RandomAccessFile temp2 = new RandomAccessFile("TempFile2", "rw");
        RandomAccessFile temp3 = new RandomAccessFile("TempFile3", "rw");
        RandomAccessFile temp4 = new RandomAccessFile("TempFile4", "rw");
    }
    
    public void sort() throws FileNotFoundException{
        RandomAccessFile filebytes = new RandomAccessFile(filename, "r");

        
    }
}
