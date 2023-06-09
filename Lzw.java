import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Lzw {

    String input;
    String newfile;
    float percent;
    int i;

    public Lzw(){
        input = "";
        newfile = "";
        percent = 0;
        i = 0;
    }

    public String toString(String file) throws IOException{
        i++; 
        int pos = file.indexOf('.');
        newfile = "./BinFiles/"+file.substring(0, pos) + "LZW" + "Compressao" + i + ".bin";
        RandomAccessFile filebytes = new RandomAccessFile(file, "rw");
        percent = filebytes.length();
        filebytes.close();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String readline;
            while((readline = reader.readLine()) != null) {
                input += readline;
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
        return input;
    }

    public String lzw_compress() throws IOException{
        long tempoInicial = System.currentTimeMillis();
        HashMap<String,Integer> dictionary = new LinkedHashMap<>();
        String[] data = (input + "").split("");
        String out = "";
        ArrayList<String> temp_out = new ArrayList<>();
        String currentChar;
        String phrase = data[0];
        int code = 256;
        for(int i=1; i<data.length;i++){
            currentChar = data[i];
            if(dictionary.get(phrase+currentChar) != null){
                phrase += currentChar;
            }
            else{
                if(phrase.length() > 1){
                    temp_out.add(Character.toString((char)dictionary.get(phrase).intValue()));
                }
                else{
                    temp_out.add(Character.toString((char)Character.codePointAt(phrase,0)));
                }

                dictionary.put(phrase+currentChar,code);
                code++;
                phrase = currentChar;
            }
        }

        if(phrase.length() > 1){
            temp_out.add(Character.toString((char)dictionary.get(phrase).intValue()));
        }
        else{
            temp_out.add(Character.toString((char)Character.codePointAt(phrase,0)));
        }

        for(String outchar:temp_out){
            out+=outchar;
        }
        System.out.println();
        System.out.println("o algoritmo executou em " + (System.currentTimeMillis() - tempoInicial)/1000 + " segundos");
        toFile(out);
        return out;
    }
    public void toFile(String out) throws IOException {
        try (RandomAccessFile filebytes = new RandomAccessFile(newfile, "rw")) {
            filebytes.writeBytes(out);
            percent = filebytes.length()/percent;
        }
        percent = 100 * (1 - percent);
        System.out.println("O percentual de redução foi de "+percent+"%");
        System.out.println();
    }

    public String lzw_extract(int version) throws IOException{
        for(int j = 0; j < version; j++){
            HashMap<Integer,String> dictionary = new LinkedHashMap<>();
            String[] data = (input + "").split("");
            String currentChar = data[0];
            String oldPhrase = currentChar;
            String out = currentChar;
            int code = 256;
            String phrase="";
            for(int i=1;i<data.length;i++){
                int currCode = Character.codePointAt(data[i],0);
                if(currCode < 256){
                    phrase = data[i];
                }
                else{
                    if(dictionary.get(currCode) != null){
                        phrase = dictionary.get(currCode);
                    }
                    else{
                        phrase = oldPhrase + currentChar;
                    }
                }
                out+=phrase;
                currentChar = phrase.substring(0,1);
                dictionary.put(code,oldPhrase+currentChar);
                code++;
                oldPhrase = phrase;
            }
            input = out;
              
        }
        try (RandomAccessFile filebytes = new RandomAccessFile("./BinFiles/decompressLZW.bin", "rw")) {
            filebytes.writeBytes(input);
        }
        return input;
    }
}
