/**
 * Key
 */
class Key {
    int id; 
    int pos;

    public Key(int id, int pos) {
        this.id = id;
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
}

public class Node {
    int num; // Node nunber of keys
    Key[] key; // Array of keys with the id and the position
    Node[] children;
    Boolean isLeaf;

    public Node() {
        num  = 0;
        key = new Key[7];
        children = new Node[8];
        isLeaf = true;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Key[] getKey() {
        return key;
    }

    public void setKey(Key[] key) {
        this.key = key;
    }

    public Node[] getChildren() {
        return children;
    }

    public void setChildren(Node[] children) {
        this.children = children;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
}
