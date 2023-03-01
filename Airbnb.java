// ===================== IMPORTING LIBRARIES ===========================

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        
        if(split.length == i){
            neighbourhood = "Downtown";
        }else{
            neighbourhood = split[i];
        }
        i++;
        
        if(split.length <= i){
            rating = -1;
        }else{
            rating = Integer.parseInt(split[i]);
        }
    }
    
    //================================== PRINT METHOD ==================================

    public void print(){
        System.out.print(id + " ");
        System.out.print(type + " ");
        System.out.print(amenities + " ");
        System.out.print(accommodates + " ");
        System.out.print(cancelation + " ");
        System.out.print(cleaning + " ");
        System.out.print(city + " ");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);	
        Calendar reviewcal = Calendar.getInstance();
        reviewcal.setTime(review);
	   
        System.out.print(sdf.format(reviewcal.getTime()) + " ");

        System.out.print(name + " ");
        System.out.print(neighbourhood + " ");
        System.out.print(rating + " ");
    }
    
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

            out.writeLong(review.getTime());

            toByteString(name, out);
            toByteString(neighbourhood, out);
            out.writeInt(rating);

            return bytes.toByteArray();
    }

    public void toByteString(String data, DataOutputStream out) throws IOException{
        out.writeInt(data.length());
        out.writeUTF(data);
    }
    
    
}