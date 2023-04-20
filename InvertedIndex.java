import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

// Class with all positions of determinated type
class Field{
    String name;
    int num;
    ArrayList<Integer> positions;

    public Field(String name, int pos) {
        positions = new ArrayList<Integer>();
        this.name = name;
        this.positions.add(pos);
        num = 1;
    }
    public Field(String name) {
        positions = new ArrayList<Integer>();
        this.name = name;
        num = 0;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Integer> getPositions() {
        return positions;
    }
    public void setPositions(int pos) {
        this.positions.add(pos);
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
}

// invertition by type and cancelation
class InvertedIndex {
    ArrayList<Field> type;
    ArrayList<Field> cancel;
    RandomAccessFile typeIndex;
    RandomAccessFile cancelIndex;
    int sizeT;
    int sizeC;

    public InvertedIndex() throws FileNotFoundException {
        //Creates two binary file and inicilizate de variables
        typeIndex = new RandomAccessFile("./BinFiles/typeIndex.bin", "rw");
        cancelIndex = new RandomAccessFile("./BinFiles/cancelIndex.bin", "rw");
        type = new ArrayList<Field>();
        cancel = new ArrayList<Field>();
        sizeT = 0;
        sizeC = 0;
    }

    public void insertType(int pos, Airbnb aux) {
        boolean ok = false; //Verify if already exists an type
        for (Field field : type) {
            if(field.name.compareTo(aux.type) == 0){
                field.setPositions(pos);
                field.num++;
                ok = true;
                break;
            }
        }
         
        if(!ok){
            Field newfield = new Field(aux.type, pos);
            type.add(newfield);
            sizeT++;
        }
    }

    public void insertCancel(int pos, Airbnb aux) {
        boolean ok = false; //Verify if already exists an cancelation
        for (Field field : cancel) {
            if(field.name.compareTo(aux.cancelation) == 0){
                field.setPositions(pos);
                field.num++;
                ok = true;
                break;
            }
        }

        if(!ok){
            Field newfield = new Field(aux.cancelation, pos);
            cancel.add(newfield);
            sizeC++;
        }
    }

    //Print method on binary file
    public void print() throws IOException {
        typeIndex.writeInt(sizeT);
        for (Field field : type){
            byte[] Stringbytes = field.name.getBytes("UTF-8");
            typeIndex.writeShort(Stringbytes.length);
            typeIndex.write(Stringbytes);
            typeIndex.writeInt(field.num);
            for (int pos : field.positions) {
                typeIndex.writeInt(pos);
            }
        }
        cancelIndex.writeInt(sizeC);
        for (Field field : cancel){
            byte[] Stringbytes = field.name.getBytes("UTF-8");
            cancelIndex.writeShort(Stringbytes.length);
            cancelIndex.write(Stringbytes);
            cancelIndex.writeInt(field.num);
            for (int pos : field.positions) {
               cancelIndex.writeInt(pos);
            }
        }

        typeIndex.close();
        cancelIndex.close();
    }

    //Search method from type
    public ArrayList<Integer> searchType(String name) throws IOException {
        ArrayList<Integer> positions = null;
        for (Field field : type) {
            if(field.name.compareTo(name) == 0){
                positions = field.getPositions();
                break;
            }
        }
        return positions;
    }

    //Search method from cancelation
    public ArrayList<Integer> searchCancel(String name) throws IOException {
        ArrayList<Integer> positions = null;
        for (Field field : cancel) {
            if(field.name.compareTo(name) == 0){
                positions = field.getPositions();
                break;
            }
        }
        return positions;
    }

    //Search method from both fields
    public ArrayList<Integer> search(String ntype, String ncancel) throws IOException {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        ArrayList<Integer> typeList = searchType(ntype);
        ArrayList<Integer> cancelList = searchCancel(ncancel);

    
        if(typeIndex != null && cancelList != null){
            for (Integer pos : typeList) {
                if(cancelList.contains(pos)){
                    positions.add(pos);
                }
            }
        }else{
            positions = null;
        }
        
        return positions;
    }

    //Creates an Inverted index from the binary file
    public void getTypeFromFile() throws IOException {
        int sizeTy = typeIndex.readInt();
        for (int i = 0; i < sizeTy; i++) {
            byte[] bytes = new byte[typeIndex.readShort()];
            typeIndex.read(bytes);    
            String name = new String(bytes);
            Field newField = new Field(name);
            int size = typeIndex.readInt();
            for (int j = 0; j < size; j++) {
                newField.positions.add(typeIndex.readInt());
            }
            type.add(newField);
        }
    }

    //Creates an Inverted index from the binary file
    public void getCFromFile() throws IOException {
        int sizeCa = cancelIndex.readInt();
        for (int i = 0; i < sizeCa; i++) {
            byte[] bytes = new byte[cancelIndex.readShort()];
            cancelIndex.read(bytes);    
            String name = new String(bytes);
            Field newField = new Field(name);
            int size = cancelIndex.readInt();
            for (int j = 0; j < size; j++) {
                newField.positions.add(cancelIndex.readInt());
            }
            cancel.add(newField);
        }
    }
}
