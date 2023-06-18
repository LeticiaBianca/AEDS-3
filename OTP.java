import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class OTP {
    String file;
    String file2;
    String x;

    public OTP(String x) {
        this.x = x;
        this.file = "./BinFiles/OTPencrypted.bin";
        this.file2 = "./BinFiles/OTPuncrypted.bin";
    }

    public void randomKey(String x) throws FileNotFoundException, IOException{
        int size = x.length();
        String c = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < size; i++){
            int index = random.nextInt(c.length());
            char randomChar = c.charAt(index);
            sb.append(randomChar);
        }

        String randomKey = sb.toString();
        crypto(randomKey);
    }



    public void crypto(String randomKey) throws FileNotFoundException, IOException{
        int[] im = charArrayToInt(x.toCharArray());
        int[] ik = charArrayToInt(randomKey.toCharArray());
        int[] data = new int[x.length()];
        
        for(int i = 0; i < x.length(); i++){
            data[i] = im[i] + ik[i];
        }
        String a =  new String(intArrayToChar(data));
        toFile(a);
    }

    public void decrypt(String randomKey) throws FileNotFoundException, IOException{
        int[] im = charArrayToInt(x.toCharArray());
        int[] ik = charArrayToInt(randomKey.toCharArray());
        int[] data = new int[x.length()];

        for(int i = 0; i < x.length(); i++){
            data[i] = im[i] - ik[i];
        }
        String a = new String(intArrayToChar(data));
        toFile2(a);
    }

    public void toFile(String crypted) throws FileNotFoundException, IOException{
        try(RandomAccessFile filebytes = new RandomAccessFile(file, "rw")){
            filebytes.writeBytes(crypted);

        }
    }

    public void toFile2(String uncrypted) throws FileNotFoundException, IOException{
        try(RandomAccessFile filebytes = new RandomAccessFile(file2, "rw")){
            filebytes.writeBytes(uncrypted);

        }
    }
    
    private char chartoInt(int i){
        return (char) i;
    }

    private char intToChar(int i){
        return (char) i;
    }
    
    public int[] charArrayToInt(char[] cc){
        int[] ii = new int[cc.length];

        for(int i = 0; i < cc.length; i++){
            ii[i] = chartoInt(cc[i]);
        }
        return ii;
    }

    private char[] intArrayToChar(int[] ii){
        char[] cc = new char[ii.length];
        for(int i = 0; i < ii.length; i++){
            cc[i] = intToChar(ii[i]);
        }
        return cc;
    }
   
}
