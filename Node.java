/**
 * Node
 */

//this class is the Node from the Btree
public class Node {

    // declaration of variables
    private int num; // Node number of keys
    private Key[] key; // Array of keys with the id and the position
    private Node[] children;//Array with all the childrens
    private Boolean isLeaf; 

   

    //empty constructor
    public Node() {
        key = new Key[7];
        for (int i = 0; i < 7; i++) {
            key[i] = null;
        }
        children = new Node[8];
        for (int i = 0; i < 8; i++) {
            children[i] = null;
        }
        num  = 0;
        isLeaf = true;
    }

    //getters and setters 
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
