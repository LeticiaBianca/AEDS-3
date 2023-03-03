//by Letícia Bianca Oliveveira and Vitor Lages de Albuquerque 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception{
        Airbnb all = new Airbnb();
        Crud crud = new Crud();
        crud.loadFile();
        // all = crud.searchId(1);
        // crud.delete(1);
        // System.out.println();
        // all.print();
        crud.update(2);
        // crud.create();

        
    }
    public static Airbnb scan(int id) throws ParseException{
        int rating, accommodates;
        String type, name, cancelation, city, cleaning, neighbourhood;
        ArrayList<String> amenities = new ArrayList<String>();
        Date review;
        String amenitiesAUX, reviewAux;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("ADDING NEW HOSTEL");
        System.out.println("");
        System.out.println();

        System.out.println("Property name: ");
        name = scan.nextLine();
        System.out.println();

        System.out.println("Property type: ");
        type = scan.nextLine();
        System.out.println();

        System.out.println("People capacity: ");
        accommodates = scan.nextInt();
        
        scan.nextLine();
        System.out.println();

        System.out.println("Cancelation policy: ");
        cancelation = scan.nextLine();
        System.out.println();

        System.out.println("Cleaning fees (V/F): ");
        cleaning = scan.nextLine();
        System.out.println();

        System.out.println("Rating: ");
        rating = scan.nextInt();

        
        scan.nextLine();
        System.out.println();
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        
        System.out.println("Last review date (DD/MM/YYYY): ");
        reviewAux =  scan.nextLine();
        review = format.parse(reviewAux);

        System.out.println("ADRESS");

        System.out.println("City: ");
        city = scan.nextLine();
        System.out.println();
        
        System.out.println("Neighbourhood: ");
        neighbourhood = scan.nextLine();
        System.out.println();

        System.out.println("AMENITIES");
        System.out.println("Type all the amenitties your property has, type 0 to stop");

        while((amenitiesAUX = scan.nextLine()).equals("0") == false){
            amenities.add(amenitiesAUX);
        }
        System.out.println();
        
        Airbnb newHostel = new Airbnb(id, type, amenities, accommodates, cancelation, cleaning, city, review, name, neighbourhood, rating);

        scan.close();
        return newHostel;
    }
}