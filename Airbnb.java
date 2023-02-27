import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Airnbnb
 */
public class Airbnb {

    protected int id;
    protected String type;
    protected ArrayList<String> amenities;
    protected int accommodates;
    protected String cancelation;
    protected String cleaning; //tem que ter uma string de tamanho fixo
    protected String city;
    protected Date review;
    protected String name;
    protected String neigh;
    protected int rating;
    
    public Airbnb(int id, String type, ArrayList<String> amenities, int accommodates, String cancelation,
            String cleaning, String city, Date review, String name, String neigh, int rating) {
        this.id = id;
        this.type = type;
        this.amenities = amenities;
        this.accommodates = accommodates;
        this.cancelation = cancelation;
        this.cleaning = cleaning;
        this.city = city;
        this.review = review;
        this.name = name;
        this.neigh = neigh;
        this.rating = rating;
    }

    public Airbnb() {

        this.type = this.cancelation = this.city = this.name = this.neigh  = this.cleaning = null;
        this.id = this.rating = this.accommodates = -1;
        this.amenities = new ArrayList<>();
        this.review = null;
    }

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

    public String getNeigh() {
        return neigh;
    }

    public void setNeigh(String neigh) {
        this.neigh = neigh;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void read(String line) throws ParseException{
       
        int i = 0;
        String[] split = line.split(";");

        // read id
        split[i] = split[i].replaceAll("\\uFEFF", "");
        id = Integer.parseInt(split[i]);
        i++;
        
        //read type
        type = split[i];
        i++;

        //ler vetor, deu preguiça de fazer agr
        int j = 0;
        String[] splitAme = split[i].split(",");
        while(splitAme[j].contains("}") == false){
            amenities.add(splitAme[j]);
            amenities.set(j, amenities.get(j).replaceAll("\"", ""));
            j++;
        }
        i++;

        //read accommodates
        accommodates = Integer.parseInt(split[i]);
        i++;

        //read cancelation
        cancelation = split[i];
        i++;

        //read cleaning
        cleaning = split[i];
        i++;

        //read city
        city = split[i];
        i++;

        //Define the data format and read review
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

        //read name
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
        
        //read neighbourhood
        if(split.length == i){
            neigh = "Downtown";
        }else{
            neigh = split[i];
        }
        i++;

        //read rating
        if(split.length <= i){
            rating = -1;
        }else{
            rating = Integer.parseInt(split[i]);
        }
    }
        
    public byte[] toByteArray() throws IOException{
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);

            out.writeInt(id);
            out.writeUTF(type);
            out.writeInt(amenities.size());
            for (String amenitie : amenities) {
                out.writeUTF(amenitie);
            }
            out.writeInt(accommodates);
            out.writeUTF(cancelation);
            out.writeUTF(cleaning);
            out.writeUTF(city);
            out.writeLong(review.getTime());
            out.writeUTF(name);
            out.writeUTF(neigh);
            out.writeInt(rating);

            return bytes.toByteArray();
    }
    
    
}