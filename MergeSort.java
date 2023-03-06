import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class MergeSort {

    public int blocksize;
    public String filename;
    

    public MergeSort() {
        this.blocksize = 1000;
        this.filename = "out.bin";
    }
    
    public void blocks() throws FileNotFoundException{
        RandomAccessFile filebytes = new RandomAccessFile(filename, "r");

        int pos = 0;
        int size;
        filebytes.seek(pos);

        while((size = filebytes.readInt()) != -1){
            pos += 4; 
            if(filebytes.readBoolean() == true){
                if(){}
                }else{
                    pos += size;
                }
            }
            else{
                pos += size;
            }
            filebytes.seek(pos);
        }
    }
}
