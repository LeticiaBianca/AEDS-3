// ===================== IMPORTING LIBRARIES ===========================

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

/**
 * Airnbnb
 */
public class Airbnb {

    
    // declaration of variables
    protected int id;
    protected String type;
    protected ArrayList<String> amenities;
    protected int accommodates;
    protected String cancelation;
    protected String cleaning;
    protected String city;
    protected Date review;
    protected String name;
    protected String neighbourhood;
    protected int rating;
    public boolean isValid;

    //constructor
    public Airbnb(int id, String type, ArrayList<String> amenities, int accommodates, String cancelation,
            String cleaning, String city, Date review, String name, String neighbourhood, int rating) {
        this.id = id;
        this.type = type;
        this.amenities = amenities;
        this.accommodates = accommodates;
        this.cancelation = cancelation;
        this.cleaning = cleaning;
        this.city = city;
        this.review = review;
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.rating = rating;
        this.isValid = true;
    }

    //empty contructor
    public Airbnb() {

        this.type = this.cancelation = this.city = this.name = this.neighbourhood  = this.cleaning = null;
        this.id = this.accommodates = -1;
        this.rating = 0;
        this.amenities = new ArrayList<>();
        this.review = null;
        this.isValid = true;
    }

    // =========================== GET AND SET======================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }

    public int getAccommodates() {
        return accommodates;
    }

    public void setAccommodates(int accommodates) {
        this.accommodates = accommodates;
    }

    public String getCacelation() {
        return cancelation;
    }

    public void setCacelation(String cancelation) {
        this.cancelation = cancelation;
    }

    public String getCleaning() {
        return cleaning;
    }

    public void setCleaning(String cleaning) {
        this.cleaning = cleaning;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getReview() {
        return review;
    }

    public void setReview(Date review) {
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getneighbourhood() {
        return neighbourhood;
    }

    public void setneighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    
    // ================================== READ METHOD =================================================
    // This method splits the csv line into ";" filling up all variables
    
    public void read(String line) throws ParseException{
       
        int i = 0;
        String[] split = line.split(";");

        // The "replaceAll" method is treating an ERROR with 'ghost' character in the beginning of the file.
        split[i] = split[i].replaceAll("\\uFEFF", "");
        id = Integer.parseInt(split[i]);
        i++;
        
        type = split[i];
        i++;
        
        
        // The following lines are treating the sequence of amenities into a list 
        //It split the data into ",", verify if has at least one amenity and replace all the undesirable characters
        int j = 0;
        String[] splitAme = split[i].split(",");
        amenities.add(splitAme[j].substring(2));
        amenities.set(j, amenities.get(j).replaceAll("\"", ""));
        j++;
        
        if(splitAme.length > j){
            while(splitAme[j].contains("}") == false){
                amenities.add(splitAme[j]);
                amenities.set(j, amenities.get(j).replaceAll("\"", ""));
                j++;
            }
        }
        i++;

        accommodates = Integer.parseInt(split[i]);
        i++;

        cancelation = split[i];
        i++;

        cleaning = split[i];
        i++;

        city = split[i];
        i++;
        
        //If the date is empty replace it with a ramdom date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        if(split[i].length() != 0){
            try {
                review = format.parse(split[i]);
            } catch (ParseException e) {
                e.printStackTrace();        
            }
        }else{
            review = format.parse("30/08/2022");
        }
        i++;
        
        //making corrections if the name has characters like " ; " and " " "
        if(split[i].charAt(0) == '\"'){
            if(split[i].substring(1).contains("\"")){
                name = split[i];
            }else{
                name = split[i];
                i++;
                while(split[i].contains("\"") == false){
                    name += split[i];
                    i++;
                }
                name += split[i];
                
                name.replaceAll("\"", "");
            }            
        }else{
            name = split[i];
        }
        i++;
        
        //filling the empty neighborhoods
        if(split.length == i){
            neighbourhood = "Downtown";
        }else{
            neighbourhood = split[i];
        }
        i++;
        
        //check if it exists before parsing
        if(split.length <= i){
            rating = -1;
        }else{
            rating = Integer.parseInt(split[i]);
        }
    }
    
    //================================== PRINT METHOD ==================================

    public void print(){
        System.out.println();
        System.out.println("Id: " + id);
        System.out.println("Type: " + type);
        System.out.println("Amenities: " + amenities);
        System.out.println("Accomodates: " + accommodates);
        System.out.println("Cancelatioin: " + cancelation);
        System.out.println("Cleaning" + cleaning);
        System.out.println("City: " + city);

        //adjusting the date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);	
        Calendar reviewcal = Calendar.getInstance();
        reviewcal.setTime(review);
        System.out.print("Date: " +sdf.format(reviewcal.getTime()));

        System.out.print("Name: " + name);
        System.out.print("Neighbourhood: " + neighbourhood);
        System.out.print("Rating: " + rating);
        System.out.println();
        System.out.println();
    }
    
    //================================== CONVERT TO BYTES METHOD ==================================
    public byte[] toByteArray() throws IOException{
        
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeBoolean(isValid);

            out.writeInt(id);
            toByteString(type, out);

            out.writeInt(amenities.size());
            for (String amenitie : amenities) {
                toByteString(amenitie, out);
            }

            out.writeInt(accommodates);
            toByteString(cancelation, out);
            toByteString(cleaning, out);
            toByteString(city, out);

            //write the time in milliseconds pas until this date in the binary file
            out.writeLong(review.getTime());

            toByteString(name, out);
            toByteString(neighbourhood, out);
            out.writeInt(rating);

            return bytes.toByteArray();
    }

    //================================== CONVERT STRING TO BYTES METHOD ==================================
    //This method write the size followed for the string in binary
    public void toByteString(String data, DataOutputStream out) throws IOException{
        byte[] Stringbytes = data.getBytes("UTF-8");
        out.writeInt(Stringbytes.length);
        out.write(Stringbytes);
    }
    
    //================================== CONVERT FROM BYTES METHOD ==================================
    //this method read a record in the bytes file and cread an object
    public void fromByteArray(int startByte, String filename) throws IOException{
        RandomAccessFile filebytes = new RandomAccessFile(filename, "rw");

        filebytes.seek(startByte);

        filebytes.readInt();
    
        isValid = filebytes.readBoolean();
        id = filebytes.readInt();

        //creat a byte array with the size read and read the bytes before converting to String 
        byte[] bytes = new byte[filebytes.readInt()];
        filebytes.read(bytes);    
        type = new String(bytes);
       
        int sizeList = filebytes.readInt();
        for(int i =0; i<sizeList; i++){
            int test = filebytes.readInt();
            byte[] bytes2 = new byte[test];
            filebytes.read(bytes2);
            amenities.add(new String(bytes2));
        }
        
        accommodates = filebytes.readInt();
         
        byte[] bytes3 = new byte[filebytes.readInt()];
        filebytes.read(bytes3);    
        cancelation = new String(bytes3);

        byte[] bytes4 = new byte[filebytes.readInt()];
        filebytes.read(bytes4);    
        cleaning = new String(bytes4);

        byte[] bytes5 = new byte[filebytes.readInt()];
        filebytes.read(bytes5);    
        city = new String(bytes5);

        long auxdata = filebytes.readLong();
        review = new Date(auxdata);

        byte[] bytes6 = new byte[filebytes.readInt()];
        filebytes.read(bytes6);    
        name = new String(bytes6);

        byte[] bytes7 = new byte[filebytes.readInt()];
        filebytes.read(bytes7);    
        neighbourhood = new String(bytes7);
        
        rating = filebytes.readInt();

        filebytes.close();
    }
}