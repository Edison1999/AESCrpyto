import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import utilities.AESImplementation;
import utilities.InvalidHexException;

public class AES {
    final static int NUM_ROUNDS_KEY_128 = 10;
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java AES <mode> <keyFilePath> <textFilePath>");
            System.exit(1);
        }
        //User input 
        String mode = args[0];
        String keyFilePath = args[1];
        String textFilePath = args[2];

        // Validation: mode = encrypt or decrypt
        if (!mode.equals("encrypt") || 
            !mode.equals("decrypt") || 
            !mode.equals("e") || 
            !mode.equals("d")) {
            System.out.println("Invalid mode. Please use 'encrypt' / 'e' or 'decrypt' / 'd'.");
            System.exit(1);
        } else if (mode.equals("encrypt") || mode.equals("e")) {
            mode = "e";
        } else {
            mode = "d";
        }

        // Validation files exist
        if (!fileExists(textFilePath) || !fileExists(keyFilePath)) {
            System.out.println("One or both of the files does not exist.");
            System.exit(1);
        }

        //Create ouput file path 
        String output_file_path= mode == "e" ? (textFilePath + ".enc"): (textFilePath + ".dec") ;

        try {
            String[] key = readLinesFromFile(keyFilePath);
            String[] text = readLinesFromFile(textFilePath);
            ArrayList<String> results = new ArrayList<String>();
            
            // Do not allow more than one key in keyFilePath
            if (key.length !=1){
                System.out.println("Invalid key file. Expected a single key.");
                System.exit(1);
            }
            for (String raw_text: text){
                if (mode == "e"){
                    results.add(AESImplementation.encrypt(raw_text, key[0], NUM_ROUNDS_KEY_128));
                }else{
                    results.add(AESImplementation.decrypt(raw_text, key[0], NUM_ROUNDS_KEY_128));
                }
            }
            writeArrayListToFile(results, output_file_path);
            System.out.println(String.format("Done! Output written to file %s", output_file_path));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Helpers
    private static String[] readLinesFromFile(String filePath) throws IOException, InvalidHexException {
        ArrayList<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isValidHex(line)){
                    throw new InvalidHexException ("Invalid hexadecimal string.");
                }
                lines.add(line);
            }
        }
        return lines.toArray(new String[0]);
    }

    private static void writeArrayListToFile(ArrayList<String> dataList, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String element : dataList) {
                writer.write(element);
                writer.write(" "); // Space separator
            }
        }
    }

    //Validation
    private static boolean isValidHex(String hexString) {
        // Regular expression to match only hexadecimal characters
        String regex = "^[0-9A-Fa-f]+$";
        return Pattern.matches(regex, hexString);
    }

    private static boolean fileExists(String textFilePath) {
        File file = new File(textFilePath);
        return file.exists() && !file.isDirectory();
    }
}