//by Letícia Bianca Oliveveira and Vitor Lages de Albuquerque 

public class Main {

    public static void main(String[] args) throws Exception{
        Airbnb all = new Airbnb();
        Crud crud = new Crud();
        crud.loadFile();
        crud.searchId(1);
        all = crud.delete(1);
        System.out.println(all.isValid);
        // all.print();
    }
}