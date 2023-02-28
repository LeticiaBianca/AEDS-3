public class Main {

    public static void main(String[] args) throws Exception{
        Airbnb all = new Airbnb();
        Crud crud = new Crud();
        crud.loadFile();
        all = crud.searchId(5);
        System.out.println(all);
    }
}