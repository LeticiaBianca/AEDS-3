//by Letícia Bianca Oliveveira and Vitor Lages de Albuquerque 

// ===================== IMPORTING LIBRARIES ===========================
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception{
        int answer;
        Crud crud = new Crud();
       
        boolean isLoaded = false;

        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("1 - to Load the file");
            System.out.println("2 - to Create a record on the file");
            System.out.println("3 - to Read a record");
            System.out.println("4 - to Update a record on the file");
            System.out.println("5 - Delete a record");
            System.out.println("6 - sorting algorithms");
            System.out.println("0 - to exit the program");
            System.out.println();
            System.out.print("Choose an operation: ");
            
            answer = scan.nextInt();
            System.out.println();
            

            switch (answer){

                case 0:
                    break;
                
                case 1:
                    crud.loadFile();
                    isLoaded = true;
                    System.out.println();
                    System.out.println("Successfully loaded!");
                    System.out.println();
                    break;

                case 2:
                    if(isLoaded == true){
                        crud.create();
                        System.out.println();
                        System.out.println("Record Successfully created!");
                        System.out.println();                   
                    }
                    else {
                        System.out.println();
                        System.out.println("File not loaded!");
                        System.out.println();
                    }
                    break;
                
                case 3:
                    if(isLoaded == true){
                        Airbnb theOne = new Airbnb();
                        int chooseId;
                        System.out.println("Type an id to be read: ");
                        chooseId = scan.nextInt();
                        theOne = crud.searchId(chooseId);
                        theOne.print();    
                    }
                    else {
                        System.out.println();
                        System.out.println("File not loaded!");
                        System.out.println();
                    }
                    break;
                
                case 4:
                    if(isLoaded == true){
                        int chooseId;
                        System.out.println("Type an id to be updated: ");
                        chooseId = scan.nextInt();
                        crud.update(chooseId);
                        System.out.println();
                        System.out.println("Record Successfully updated!");
                        System.out.println();
                    }
                    else {
                        System.out.println();
                        System.out.println("File not loaded!");
                        System.out.println();
                    }
                    break;

                case 5:
                    if(isLoaded){
                        int chooseId;
                        System.out.println("Type an id to be deleted: ");
                        chooseId = scan.nextInt();
                        crud.delete(chooseId);
                        System.out.println();
                        System.out.println("Record Successfully deleted!");
                        System.out.println();
                    }
                    else {
                        System.out.println();
                        System.out.println("File not loaded!");
                        System.out.println();
                    }
                    break;
                case 6:
                    System.out.println();
                    System.out.println("1 - to Sort with Extern Merge Sort");
                    System.out.println("2 - to Sort with Variable Blocks Merge Sort");
                    System.out.println("3 - to Sort with Selection by Substitution");
                    
                    System.out.println();
                    System.out.print("Choose an operation: ");
                    int option = scan.nextInt();

                    switch(option){
                        case 1:
                        break;
                        case 2:
                        break;
                        case 3:
                        break;
                    }
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid operation");  
                    System.out.println();  
            }
            scan.close();
        }
        while(answer != 0);
    }

    //============================ CREATE AN OBJECT METHOD ============================
    //this method interact with the user to fill a Airbnb object and is used in create and update method 
    public static Airbnb scan(int id) throws ParseException{
        int rating, accommodates;
        String type, name, cancelation, city, cleaning, neighbourhood;
        ArrayList<String> amenities = new ArrayList<String>();
        Date review;
        String amenitiesAUX, reviewAux;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println();
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
        
        //adjusting the date format
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

        //filling a list of amenitties
        while((amenitiesAUX = scan.nextLine()).equals("0") == false){
            amenities.add(amenitiesAUX);
        }
        System.out.println();
        
        Airbnb newHostel = new Airbnb(id, type, amenities, accommodates, cancelation, cleaning, city, review, name, neighbourhood, rating);

        scan.close();
        return newHostel;
    }
}