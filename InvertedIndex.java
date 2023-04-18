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

    public InvertedIndex() throws FileNotFoundException {
        typeIndex = new RandomAccessFile("typeIndex.bin", "rw");
        cancelIndex = new RandomAccessFile("cancelIndex.bin", "rw");
        type = new ArrayList<Field>();
        cancel = new ArrayList<Field>();
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
        }
    }

    public void print() throws IOException {
        for (Field field : type){
            byte[] Stringbytes = field.name.getBytes("UTF-8");
            typeIndex.write(Stringbytes);
            typeIndex.writeInt(field.num);
            for (int pos : field.positions) {
                typeIndex.writeInt(pos);
            }
        }
        for (Field field : cancel){
            byte[] Stringbytes = field.name.getBytes("UTF-8");
            typeIndex.write(Stringbytes);
            typeIndex.writeInt(field.num);
            for (int pos : field.positions) {
                typeIndex.writeInt(pos);
            }
        }
    }
}
