import java.io.*;

public class AES {
    
    // encrypts the string s
    public static String encrypt(String s, int rounds) {
        byte[][] state = stringToByteArray(s);
        addRoundKey(state);
        for (int i = 0; i < rounds-1; i++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state);
        }
        subBytes(state);
        shiftRows(state);
        addRoundKey(state);
        // convert the state array to a string and return it as output
        return byteArrayToString(state);
    }

    // addRoundKey step for encryption
    public static void addRoundKey(byte[][] state) {

    }

    // subBytes step for encryption
    public static void subBytes(byte[][] state) {

    }

    // shiftRows step for encryption
    public static void shiftRows(byte[][] state) {

    }

    // mixColumns step for encryption
    public static void mixColumns(byte[][] state) {

    }

    // convert a hex string s to a byte array
    public static byte[][] stringToByteArray(String s) {

    }

    // convert a byte array arr to a string
    public static String byteArrayToString(byte[][] arr) {

    }

    // decrypts the String s
    public static String decrypt(String s, int rounds) {
        byte[][] state = stringToByteArray(s);
        addRoundKey(state);
        shiftRows(state);
        subBytes(state);
        for (int i = 0; i < rounds-1; i++) {
            invAddRoundKey(state);
            invMixColumns(state);
            invShiftRows(state);
            invSubBytes(state);
        }
        addRoundKey(state);
    }

    // inverse addRoundKey step for decryption
    public static void invAddRoundKey(byte[][] state) {

    }

    // inverse subBytes step for decryption
    public static void invSubBytes(byte[][] state) {

    }

    // inverse shiftRows step for decryption
    public static void invShiftRows(byte[][] state) {

    }

    // inverse mixColumns step for decryption
    public static void invMixColumns(byte[][] state) {

    }

    public static int add(int i) {
        return i+1;
    }

    public static void main(String[] args) {
        
    }
}
