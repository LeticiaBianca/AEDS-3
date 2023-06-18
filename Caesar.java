import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Caesar {
    String file;
    
    public Caesar() {
        this.file = "./BinFiles/CaesarCrypto.bin";
    }

    public void crypto(String text, int key) throws FileNotFoundException, IOException{
        StringBuilder cryptotext = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            int ascii = ((int)text.charAt(i)) + key;

            while(ascii > 126){
                ascii -= 94;
            }

            cryptotext.append((char)ascii);
            
        }
        toFile(cryptotext.toString());
    }

    public String decrypt(String cryptotext, int key){
        StringBuilder decrypttext = new StringBuilder();
        for(int i = 0; i < cryptotext.length(); i++){
            int ascii = ((int)cryptotext.charAt(i)) - key;

            while(ascii < 32){
                ascii += 94;
            }

            decrypttext.append((char) ascii);
        }
        return decrypttext.toString();
    }
    // public void fillfile(){
    //     file = "./BinFiles/CaesarCrypto";
    // }

    public void toFile(String cryptotext) throws FileNotFoundException, IOException{
        try(RandomAccessFile filebytes = new RandomAccessFile(file, "rw")){
            filebytes.writeBytes(cryptotext);
        }
    }
}
