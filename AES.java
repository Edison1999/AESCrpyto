import java.io.*;

public class AES implements AESImplementation {
    
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
        throw new UnsupportedOperationException("Unimplemented method 'addRoundKey'");
    }

    // subBytes step for encryption
    public static byte[][] subBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int row = (state[i][j] & 0xF0) >> 4;
                int col = state[i][j] & 0x0F;
                result[i][j] = sbox0[row * 16 + col];
            }
        }
        return result;
    }

    // shiftRows step for encryption
    public static byte[][] shiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0]=state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shiftLeft(state[i], i);
        }
        return result;
    }

    private static byte[] shiftLeft(byte[] bs, int shift) {
        byte[] shifted_arr=new byte [bs.length];
        int left=0;
        for (int i = 0; i < bs.length; i++) {
            if (i + shift < bs.length){
                shifted_arr[i] = bs[i+ shift];
            }else {
                shifted_arr[i] = bs[left];
                left+=1;
            }
        }
        return shifted_arr;
    }

    // mixColumns step for encryption
    public static void mixColumns(byte[][] state) {
        throw new UnsupportedOperationException("Unimplemented method 'mixColumns'");
    }

    // convert a hex string s to a byte array
    public static byte[][] stringToByteArray(String s) {
        throw new UnsupportedOperationException("Unimplemented method 'stringToByteArray'");
    }

    // convert a byte array arr to a string
    public static String byteArrayToString(byte[][] arr) {
        throw new UnsupportedOperationException("Unimplemented method 'byteArrayToString'");
    }

    // decrypts the String s
    public static void decrypt(String s, int rounds) {
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
        throw new UnsupportedOperationException("Unimplemented method 'invAddRoundKey'");
    }

    // inverse subBytes step for decryption
    public static byte[][] invSubBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int row = (state[i][j] & 0xF0) >> 4;
                int col = state[i][j] & 0x0F;
                result[i][j] = sbox1[row * 16 + col];
            }
        }
        return result;
    }

    // inverse shiftRows step for decryption
    public static byte[][] invShiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0]=state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shiftRight(state[i], i);
        }
        return result;
    }

    private static byte[] shiftRight(byte[] bs, int shift) {
        byte[] shifted_arr=new byte [bs.length];
        int right=bs.length-shift;
        for (int i = 0; i < bs.length; i++) {
            if (right > bs.length -1){
                shifted_arr[i] = bs[right];
                right+=1;
            }else {
                shifted_arr[i] = bs[i-shift];
            }
        }
        return shifted_arr;
    }

    // inverse mixColumns step for decryption
    public static void invMixColumns(byte[][] state) {
        throw new UnsupportedOperationException("Unimplemented method 'invMixColumns'");
    }

    public static int add(int i) {
        return i+1;
    }

    public static void main(String[] args) {
        String plainText = "7b2e9f8c5a6d3f10e57cb4aef906d2a4";
        String key = "9a3f7b0e5c2d8a1f4b6e0c3d2f5a8e9d";
         

        byte[][] arr_plainText = new byte[4][4];
        byte[][] arr_key = new byte[4][4];
    
        for (int i = 0; i < plainText.length(); i+=2) { 
            int row_idx = (i / 2) / 4;
            int col_idx = (i / 2) % 4;
            arr_plainText[col_idx][row_idx] = (byte) Integer.parseInt(plainText.substring(i, i+2), 16);
            arr_key[row_idx][col_idx] = (byte) Integer.parseInt(key.substring(i, i+2), 16);

        } 

    }
}
