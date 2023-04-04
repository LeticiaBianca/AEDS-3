/**
 * ArvoreB
 */
public class ArvoreB {

    Node root;

    public ArvoreB(Node root) {
        this.root = root;
    }
    public ArvoreB() {
        this.root = new Node();
    }
    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }

}