import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

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
        typeIndex = new RandomAccessFile("typeIndex.bin", "rw");
        cancelIndex = new RandomAccessFile("cancelIndex.bin", "rw");
        type = new ArrayList<Field>();
        cancel = new ArrayList<Field>();
        sizeT = 0;
        sizeC = 0;
    }

    public void insertType(int pos, Airbnb aux) {
        boolean ok = false;
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
        boolean ok = false;
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

    
    public ArrayList<Integer> search(String ntype, String ncancel) throws IOException {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        ArrayList<Integer> typeList = searchType(ntype);
        ArrayList<Integer> cancelList = searchCancel(ncancel);

        for (Integer pos : typeList) {
            if(cancelList.contains(pos)){
                positions.add(pos);
            }
        }
        return positions;
    }

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
