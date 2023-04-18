import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ArvoreB
 */
public class Btree {

    Node root;
    public String BtreeFile;
    RandomAccessFile fileBtree;

    public Btree(Node root) {
        this.root = root;
    }
    public Btree() throws FileNotFoundException{
        this.root = new Node();
        this.BtreeFile = "Btree.bin";
        
       this.fileBtree = new RandomAccessFile(BtreeFile, "rw");
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
            root.setNum(1);
        }else{
            if(root.getNum() == 7){
                System.out.println("Aqui");
                Node newRoot = new Node();
                newRoot.setIsLeaf(false);
                newRoot.setChildren(root, 0);
                newRoot.splitChildren(root, 0);
                int i = 0;
                if(newRoot.getKey(0).id < k.id){
                    i++;
                }
                newRoot.getChildren(i).insertNotFull(k);
                root = newRoot;
            }else{            
                root.insertNotFull(k);
            }
        }
    }
    
    private void splitNode(Node r, int i, Node newNode) {
        int minimumsize = 3;

        Node newNode2 = new Node();
        newNode2.setIsLeaf(newNode.getIsLeaf());

        for (int j = 0; j < minimumsize; j++) {
            newNode2.setKey(newNode.getKey(j), j+minimumsize+1);
            newNode.setNum(newNode.getNum()-1);
            newNode2.setNum(newNode2.getNum()+1);
        }

        if(newNode2.getIsLeaf() == false){
            for (int j = 0; j < minimumsize; j++){
                newNode2.setChildren(newNode.getChildren(j+minimumsize+1 ), j);
            }
        }
        for (int j = r.getNum(); j > i; j--) {
            r.setChildren(r.getChildren(j), j+1);
        }

        r.setChildren(newNode2, i+1);

        for (int j = r.getNum(); j > i; j--) {
            r.setKey(r.getKey(j-1), j+1);
        }

        r.setKey(newNode.getKey(minimumsize-1), i);
        newNode.setNum(newNode.getNum()-1);
        r.setNum(r.getNum()+1);
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
        int i = 0;
        while (i < x.getNum()-1 && (k > x.getKey(i).getId())) { 
            i++;
        }
        if ((i < x.getNum()-1) && (k == x.getKey(i).getId())) {
            return x.getKey(i);
        }
        if(x.getIsLeaf() == false){
            return searchKey(k, x.getChildren(i));
        }else{
            return null;
        }
    }

}