import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Btree
 */
public class Btree {

    Node root;
    RandomAccessFile fileBtree;

    public Btree() throws FileNotFoundException{
        this.root = new Node();
       this.fileBtree = new RandomAccessFile("Btree.bin", "rw");
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void insert(Key k){//insertion of an element in the root
        if(root.getNum() == 0){//verify if the root is empty
            root.setKey(k, 0);//insertion of the key in the position 0
            root.setNum(root.getNum()+1);
        }else{
            Node r = root;
            if (r.getNum() == 7) {
                Node s = new Node();
                s.setIsLeaf(false);
                s.setNum(0);
                s.setChildren(r, 0);
                splitNode(s, 0, r);
                insertNotFull(s, k);
                root = s;
            }else{
                insertNotFull(r, k);
            }
        }
    }
    
    private void splitNode(Node x, int i, Node y) {
        int minimumsize = 3;

        Node z = new Node();
        z.setIsLeaf(y.getIsLeaf());
        z.setNum(minimumsize);

        for (int j = 0; j < minimumsize; j++) {

            z.setKey(y.getKey(j+4), j);
            y.setNum(y.getNum()-1);      
        }

        if (!y.getIsLeaf()) {
            for (int j = 0; j < minimumsize + 1; j++) {
                z.setChildren(y.getChildren(j+4), j);
            }
        }

        y.setNum(minimumsize);

        for (int j = x.getNum(); j > i; j--) { 
            x.setChildren(x.getChildren(j), j+1);
        }

        x.setChildren(z, i+1);

        for (int j = x.getNum(); j > i; j--) {
            x.setKey(x.getKey(j-1), j);
        }
        
        x.setKey(y.getKey(minimumsize), i);
        x.setNum(x.getNum() + 1);
    }

    public void insertNotFull(Node node, Key k) {
        int i = node.getNum() - 1;

        if(node.getIsLeaf()){
            while(i>=0 && k.getId() < node.getKey(i).getId()){
                node.setKey(node.getKey(i), i+1);
                i--;
            }
            i++;
            node.setKey(k, i);
            node.setNum(node.getNum()+1);
        }else{
            while(i>=0 && k.getId() < node.getKey(i).getId()){;
                i--;
            }
            i++;
            if(node.getChildren(i).getNum() == 7){
                splitNode(node, i, node.getChildren(i));
                if(k.getId() > node.getKey(i).getId()){
                    i++;
                }
            }
            insertNotFull(node.getChildren(i), k);
        }
    }

    public void printFile() throws IOException {
        int position = 4;
        fileBtree.seek(0);
        fileBtree.writeInt(position);
        printFile(root, position);
    }

    private void printFile(Node x, int position) throws IOException{
        fileBtree.writeInt(x.getNum());
        fileBtree.writeInt(position += 92);
        for (int i = 0; i < 7; i++) {
            if(i < x.getNum()){
                fileBtree.writeInt(x.getKey(i).getId());
                fileBtree.writeInt(x.getKey(i).getPos());
                if(x.getIsLeaf() == false){
                    fileBtree.writeInt(position+=92);
                }else{
                    fileBtree.writeInt(-1);
                }
            }else{
                fileBtree.writeInt(0);
                fileBtree.writeInt(0);
                fileBtree.writeInt(-1);
            }
        }
        if(x.getIsLeaf() == false){
            for (int i = 0; i <= x.getNum(); i++) {    
               printFile(x.getChildren(i), position);
            }
        }
    }

    public Key searchKey(int k){
        return searchKey(k, root);
    }

    private Key searchKey(int k, Node x) {
        int i = 1;
        while (i <= x.getNum() && (k > x.getKey(i-1).getId())) { 
            i++;
        }
        if ((i <= x.getNum()) && (k == x.getKey(i-1).getId())) {
            return x.getKey(i);
        }else if(x.getIsLeaf()){
            return null;
        }else{
            return searchKey(k, x.getChildren(i-1));
        }
    }

}