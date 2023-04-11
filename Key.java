/**
 * Key
 */

 //this class is used to storage in the same place the id and his position in the file
class Key {
    // declaration of variables
    int id; 
    int pos;

    //contructor
    public Key(int id, int pos) {
        this.id = id;
        this.pos = pos;
    }

    //getters and setters
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
