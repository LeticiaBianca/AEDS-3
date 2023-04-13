import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// following the intrction from the TP2 the size of the bucket is 293

public class Hashing {
    private int p;
    private int bucketSize;
    private RandomAccessFile fileHash;
    private ArrayList<ArrayList<Key>> bucketList;
    
    public Hashing(int p) throws FileNotFoundException {
        this.p = p;
        bucketSize = 293;
        fileHash = new RandomAccessFile("Hash.bin", "rw");

        bucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, p));
        for (int i = 0; i < (int)Math.pow(2, p); i++) { 
            bucketList.add(new ArrayList<Key>(bucketSize));
        }
    }
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

    private int h(int id, int p){
        return id % ((int)Math.pow(2, p));
    }

    public void insert(Key k){
        int index = h(k.id, p);

        if(bucketList.get(index).size() >= bucketSize){
            int newp = p + 1;
            ArrayList<ArrayList<Key>> newbucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, newp));

            for (int i = 0; i < (int)Math.pow(2, newp); i++) { 
                newbucketList.add(new ArrayList<Key>(bucketSize));
            }
            
            for (ArrayList<Key> bucket : bucketList) {
                for (Key each : bucket){
                    Key key = each;
                    int newIndex = h(key.getId(), newp);
                    newbucketList.get(newIndex).add(key);
                }
            }
            p = newp;
            bucketList = newbucketList;

            index = h(k.getId(), p);
        }
        bucketList.get(index).add(k);
    }

    public void printFile() throws IOException {
        fileHash.writeInt(p);
        for (ArrayList<Key> directory : bucketList) {
            fileHash.writeInt(directory.size());
            for (Key each : directory) {
                fileHash.writeInt(each.id);
                fileHash.writeInt(each.pos);
            }
        }
    }

    public void getFromFile() throws IOException{
        p = fileHash.readInt();
        bucketList = new ArrayList<ArrayList<Key>>((int)Math.pow(2, p));
        for (int i = 0; i < (int)Math.pow(2, p); i++) { 
            bucketList.add(new ArrayList<Key>(bucketSize));
        }

        int j = 0;
        while(fileHash.getFilePointer() < fileHash.length()){
            int size = fileHash.readInt();
            for(int i=0; i<size; i++){
                int id = fileHash.readInt();
                int pos = fileHash.readInt();
                Key k = new Key(id, pos);
                bucketList.get(j).add(k);
            }
            j++;
        }
    }
    
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
