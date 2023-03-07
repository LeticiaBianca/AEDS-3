import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class MergeSort {

    public int blocksize;
    public String filename;
    

    public MergeSort() {
        this.blocksize = 100;
        this.filename = "out.bin";
    }
    
    public void blocks() throws FileNotFoundException{
        RandomAccessFile filebytes = new RandomAccessFile(filename, "r");

    }
}
