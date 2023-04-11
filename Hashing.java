import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// following the intrction from the TP2 the size of the bucket is 293

public class Hashing {
    private int p;
    private int bucketSize;
    private ArrayList<Integer> directory;
    RandomAccessFile fileHash;
    private ArrayList<Key[]> bucketList;
    
    public Hashing(int p) throws FileNotFoundException {
        this.p = p;
        bucketSize = 293;
        directory = new ArrayList<Integer>((int)Math.pow(2, p));
        bucketList = new ArrayList<Key[]>();
        fileHash = new RandomAccessFile("Hash.bin", "rw");
    }

    public int getP() {
        return p;
    }


    public void setP(int p) {
        this.p = p;
    }


    public int getBucketSize() {
        return bucketSize;
    }

    public ArrayList<Integer> getDirectory() {
        return directory;
    }


    public void setDirectory(ArrayList<Integer> directory) {
        this.directory = directory;
    }


    public RandomAccessFile getFileHash() {
        return fileHash;
    }


    public void setFileHash(RandomAccessFile fileHash) {
        this.fileHash = fileHash;
    }


    public ArrayList<Key[]> getBucketList() {
        return bucketList;
    }


    public void setBucketList(ArrayList<Key[]> bucketList) {
        this.bucketList = bucketList;
    }

    private int h(int id, int p){
        return id % ((int)Math.pow(2, p));
    }

    public void insert(Key k){
        int index = h(k.id, p);

        if(directory.get(index) == bucketSize){
            //tem que ter um p interno de cada 1???
        }
    }
}
