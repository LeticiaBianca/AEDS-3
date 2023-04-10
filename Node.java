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

/**
 * Node
 */

public class Node {
    private int num; // Node number of keys
    private Key[] key; // Array of keys with the id and the position
    private Node[] children;
    private Boolean isLeaf;

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

    public Key getKey(int position) {
        return key[position];
    }

    public void setKey(Key key, int position) {
        this.key[position] = key;
    }

    public Node getChildren(int position) {
        return children[position];
    }

    public void setChildren(Node children, int position) {
        this.children[position] = children;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
}
