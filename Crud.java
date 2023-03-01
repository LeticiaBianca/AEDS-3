import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

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

    public Airbnb create(){
        int rating, accommodates;
        String type, name, cancelation, city, cleaning, neighbourhood;
        ArrayList<String> amenities = new ArrayList<String>();
        Date review;

        String amenitiesAUX;

        Scanner scan = new Scanner(System.in);
        
        System.out.println("ADDING NEW HOSTEL");
        System.out.println("");

        System.out.println("Property name: ");
        name = scan.nextLine();

        System.out.println("Property type: ");
        type = scan.nextLine();

        System.out.println("People capacity: ");
        accommodates = scan.nextInt();

        scan.nextLine();

        System.out.println("Cancelation policy: ");
        cancelation = scan.nextLine();

        System.out.println("Cleaning fees (V/F): ");
        cleaning = scan.nextLine();

        System.out.println("Rating: ");
        rating = scan.nextInt();

        scan.nextLine();

        System.out.println("ADRESS");

        System.out.println("City: ");
        city = scan.nextLine();
        
        System.out.println("Neighbourhood: ");
        neighbourhood = scan.nextLine();

        System.out.println("AMENITIES");
        System.out.println("Type all the amenitties your property has, type 0 to stop");

        while((amenitiesAUX = scan.nextLine()).equals("0") == false){
            amenities.add(amenitiesAUX);
        }

        return null;
    }
}

