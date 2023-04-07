/**
 * ArvoreB
 */
public class ArvoreB {

    int num;
    Node root;

    public ArvoreB(Node root, int num) {
        this.root = root;
        this.num = num;
    }
    public ArvoreB() {
        this.root = new Node();
        num = 0;
    }
    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public void insert(Key k){//insertion of an element in the root
        if(root.getNum() == 0){//verify if the root is empty
            root.setKey(k, 0);//insertion of the key in the position 0
            root.setNum(1);
        }else{
            Node r = root;
            if(root.getNum() == 7){// verify if the root is full
                Node newNode = new Node();
                root = new Node();
                newNode.setIsLeaf(false);
                newNode.setChildren(r, 0);
                splitNode(newNode, 0, r);
                insertNotFull(newNode, k);
            }else{
                insertNotFull(r, k);
            }
        }
        num++;
        //CÓDIGO PARA JOGAR NO ARQUIVO!
    }
    
    private void splitNode(Node r, int i, Node newNode) {
        int minimumsize = 3;

        Node newNode2 = new Node();
        newNode2.setIsLeaf(newNode.getIsLeaf());

        for (int j = 0; j < minimumsize; j++) {
            newNode2.setKey(newNode.getKey(j), j+minimumsize);
            newNode.setNum(newNode.getNum()-1);
        }

        if(newNode2.getIsLeaf() == false){
            for (int j = 0; j < minimumsize; j++){
                newNode2.setChildren(newNode.getChildren(j+minimumsize), j);
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

}