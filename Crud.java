import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Crud {
    public int lastId;
    public int fileLength;
    public ArrayList<Airbnb> hostel;

    public Crud() {
        this.lastId = 0;
        this.fileLength = 0;
        this.hostel = new ArrayList<>();
    }

    public void loadFile() throws ParseException, IOException{
        ArrayList<String> line = new ArrayList<>();
        String filename = "out.bin";
        BufferedReader reader = null;
        byte[] bytesdata;
        String file = "Airbnb.csv";
        String readline;

        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

        try{
            reader = new BufferedReader(new FileReader(file));
            while((readline = reader.readLine()) != null) {
                line.add(readline);
                hostel.add(fileLength, new Airbnb());
                hostel.get(fileLength).read(line.get(fileLength));
                bytesdata = hostel.get(fileLength).toByteArray();
                filebytes.writeInt(bytesdata.length);
                filebytes.write(bytesdata);
                lastId = hostel.get(fileLength).id;
                fileLength++;
            }
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally{
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        filebytes.close();
    }

    public void negativeException(int id) throws Exception{
        if(id < 0){
            throw new Exception("INVALID ID!!!");
        }
        
    }

    // search id
    public Airbnb searchId(int id) throws Exception{
        Airbnb aux = new Airbnb();
        for(int i = 0; i < fileLength; i++){
            if(hostel.get(i).id == id){
                aux = hostel.get(i);
                i = fileLength;
            }
            else{
                aux = null;
            }            
        }
        if(aux == null){
            System.out.println("ID NOT FOUND!");
        }
        return aux;
    }

    public Airbnb delete(int id)throws Exception{
        Airbnb aux = new Airbnb();
        for(int i = 0; i < fileLength; i++){
            if(hostel.get(i).id == id){
                aux = hostel.get(i);
                aux.isValid = false;
                hostel.set(i, aux);
                i = fileLength;
            }
        }
        return aux;
    }

    public Airbnb create() throws ParseException{
        int rating, accommodates, id;
        String type, name, cancelation, city, cleaning, neighbourhood;
        ArrayList<String> amenities = new ArrayList<String>();
        Date review;
        String amenitiesAUX, reviewAux;

        id = lastId += 1;

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
        hostel.add();
        
        return null;
    }
}

