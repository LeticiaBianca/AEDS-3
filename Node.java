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

    public void splitChildren(Node c1, int i) {
        Node c2 = new Node();
        c2.isLeaf = c2.isLeaf;
        int k = 0;
        for (int j = 6; j > 3; j--) {
            c2.setKey(c1.getKey(j), k);
            k++;
            c2.num++;
            c1.num--;
        }
        if(c1.isLeaf == false){
            k=0;
            for (int j = 7; j > 3; j--) {
                c2.setChildren(c1.getChildren(j), k);
                k++;
            }
        }
        setChildren(c2, i+1);
        setKey(c1.getKey(3), i);
        c1.num--;
    }

    public void insertNotFull(Key k) {
        int i = num - 1;
        if(isLeaf){
            while(i >= 0 && getKey(i).id > k.id){
                setKey(getKey(i), i+1);
                i--;
            }
            setKey(k, i+1);
            num++;
        }else{
            while(i >= 0 && getKey(i).id > k.id){
                i--;
            }
            if(getChildren(i+1).getNum() == 6){
                splitChildren(getChildren(i+1), i+1);
                if(getKey(i+1).id < k.id){
                    i++;
                }
            }
            getChildren(i+1).insertNotFull(k);
        }
    }
    
}
