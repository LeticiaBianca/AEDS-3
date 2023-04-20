import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// Using 5% of file length as bucket size

public class Hashing {
    private int p;
    private int bucketSize;
    private RandomAccessFile fileHash;
    private ArrayList<ArrayList<Key>> bucketList;
    
    // Craetes a binary file 
    public Hashing(int p) throws FileNotFoundException {
        this.p = p;
        bucketSize = 293;
        fileHash = new RandomAccessFile("./BinFiles/Hash.bin", "rw");

        //initialize the bucket with accurate size 
        bucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, p));
        for (int i = 0; i < (int)Math.pow(2, p); i++) { 
            bucketList.add(new ArrayList<Key>(bucketSize));
        }
    }

    //Read from binary hash file
    public Hashing(String name) throws IOException {
        bucketSize = 293;
        fileHash = new RandomAccessFile(name, "rw");

       getFromFile();
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

    public RandomAccessFile getFileHash() {
        return fileHash;
    }


    public void setFileHash(RandomAccessFile fileHash) {
        this.fileHash = fileHash;
    }


    public ArrayList<ArrayList<Key>> getBucketList() {
        return bucketList;
    }


    public void setBucketList(ArrayList<ArrayList<Key>> bucketList) {
        this.bucketList = bucketList;
    }

    //Hash method
    private int h(int id, int p){
        return id % ((int)Math.pow(2, p));
    }

    //Insert method
    public void insert(Key k){
        int index = h(k.id, p);

        //Verify if its full
        if(bucketList.get(index).size() >= bucketSize){
            int newp = p + 1;
            ArrayList<ArrayList<Key>> newbucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, newp));

            for (int i = 0; i < (int)Math.pow(2, newp); i++) { 
                newbucketList.add(new ArrayList<Key>(bucketSize));
            }
            
            //Rearrange all data
            for (ArrayList<Key> bucket : bucketList) {
                for (Key each : bucket){
                    Key key = each;
                    int newIndex = h(key.getId(), newp);
                    newbucketList.get(newIndex).add(key);
                }
            }
            p = newp;
            bucketList = newbucketList;

            //defi a new hash
            index = h(k.getId(), p);
        }
        //Insert a new key
        bucketList.get(index).add(k);
    }

    //Print method
    public void printFile() throws IOException {
        fileHash.writeInt(p);
        fileHash.writeInt(bucketList.size());
        for (ArrayList<Key> directory : bucketList) {
            fileHash.writeInt(directory.size());
            for (Key each : directory) {
                fileHash.writeInt(each.id);
                fileHash.writeInt(each.pos);
            }
        }
    }

    //get data from binary file
    public void getFromFile() throws IOException{
        p = fileHash.readInt();
        bucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, p));
        for (int i = 0; i < (int)Math.pow(2, p); i++) { 
            bucketList.add(new ArrayList<Key>(bucketSize));
        }

        int size1 = fileHash.readInt();
        for (int n = 0; n < size1; n++) {
            int size = fileHash.readInt();
            for(int i=0; i<size; i++){
                int id = fileHash.readInt();
                int pos = fileHash.readInt();
                Key k = new Key(id, pos);
                bucketList.get(n).add(k);
            }  
        }
    }
    
    //Search method
    public Key search(int k) {
        int index = h(k, p);
        Key key = null;

        for (Key each : bucketList.get(index)) {
            if(each.id == k){
                key = each;
            }
        }
        return key;
    }
}
