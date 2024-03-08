import java.io.*;

public class AES implements AESImplementation {

    public static String key = "23c4c7e11ab3c79a420446c5c25d3a18";
    
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
        byte[][] keyArr = stringToByteArray(key);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int temp = ((int) state[i][j]) ^ ((int) keyArr[i][j]);
                state[i][j] = (byte) temp;
            }
        }
    }

    // subBytes step for encryption
    public static byte[][] subBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                result[i][j] = getByteFromSBox(state[i][j]);
            }
        }
        return result;
    }

    // get the byte value associated wiht byte b from Forward S-box
    public static byte getByteFromSBox(byte b) {
        int i = Byte.toUnsignedInt(b);
        return FORWARD_S_BOX[i];
    }
    
    // get the byte value associated wiht byte b from Inverse S-box
    public static byte getByteFromInvSBox(byte b) {
        int i = Byte.toUnsignedInt(b);
        return INVERSE_S_BOX[i];
    }

    // shiftRows step for encryption
    public static byte[][] shiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0]=state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shiftRight(state[i], i);
        }
        return result;
    }

    public static byte[] shiftRight(byte[] bs, int shift) {
        byte[] shifted_arr = new byte[bs.length];
        int shiftTo;
        for (int i = 0; i < bs.length; i++) {
            shiftTo = (i + shift) % 4;
            shifted_arr[i] = bs[shiftTo];
        }
        return shifted_arr;
    }

    // mixColumns step for encryption for mixColumns
    public static byte[][] mixColumns(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int col = 0; col < state[0].length; col++) {
            result[0][col] = (byte) (GMul((byte)0x02, state[0][col]) ^ GMul((byte)0x03,state[1][col]) ^ state[2][col] ^ state[3][col]);
            result[1][col] = (byte) (state[0][col] ^ GMul((byte)0x02, state[1][col]) ^ GMul((byte)0x03, state[2][col]) ^ state[3][col]);
            result[2][col] = (byte) (state[0][col] ^ state[1][col] ^ GMul((byte)0x02 ,state[2][col]) ^ GMul((byte)0x03, state[3][col]));
            result[3][col] = (byte) (GMul((byte)0x03 ,state[0][col]) ^ state[1][col] ^ state[2][col] ^ GMul((byte)0x02 , state[3][col]));   
        }
        return result;
    }

    // Galois Field (256) Multiplication of two Bytes for mixColumns
    private static byte GMul(byte a, byte b) {
        byte p = 0;
        for (int counter = 0; counter < 8; counter++) {
            if ((b & 1) != 0) {
                p ^= a;
            }
            boolean hi_bit_set = (a & 0x80) != 0;
            a <<= 1;
            if (hi_bit_set) {
                a ^= 0x1B; /* x^8 + x^4 + x^3 + x + 1 */
            }
            b >>= 1;
        }
        return p;
    }

    // convert a hex string s to a byte array
    public static byte[][] stringToByteArray(String s) {
        byte[][] result = new byte[4][4];
        String temp;
        int start;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                start = i * 8 + j * 2;
                temp = s.substring(start, start + 2);
                result[i][j] = hexStringToByte(temp);
            }
        }
        return result;
    }

    // convert a hex string s to a byte
    public static byte hexStringToByte(String s) {
        int i = Integer.parseInt(s, 16);
        byte b = (byte) i;
        return b;
    }

    // convert a byte array arr to a string
    public static String byteArrayToString(byte[][] arr) {
        String temp = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                temp += byteToHexString(arr[i][j]);
            }
        }
        return temp;
    }

    // convert a byte b to a hex string
    public static String byteToHexString(byte b) {
        int i = b & 0xff;
        String s = Integer.toHexString(i);
        if (s.length() % 2 == 1) { // if only one digit, pad with zero
            s = "0" + s;
        }
        return s;
    }

    // decrypts the String s
    public static void decrypt(String s, int rounds) {
        byte[][] state = stringToByteArray(s);
        addRoundKey(state);
        shiftRows(state);
        subBytes(state);
        for (int i = 0; i < rounds-1; i++) {
            addRoundKey(state);
            invMixColumns(state);
            invShiftRows(state);
            invSubBytes(state);
        }
        addRoundKey(state);
    }

    // inverse subBytes step for decryption
    public static byte[][] invSubBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                result[i][j] = getByteFromInvSBox(state[i][j]);
            }
        }
        return result;
    }

    // inverse shiftRows step for decryption
    public static byte[][] invShiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0] = state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shiftRight(state[i], i);
        }
        return result;
    }

    public static byte[] shiftLeft(byte[] bs, int shift) {
        byte[] shifted_arr = new byte[bs.length];
        int shiftTo;
        for (int i = 0; i < bs.length; i++) {
            shiftTo = (i + shift*-1 + 4) % 4;
            shifted_arr[i] = bs[shiftTo];
        }
        return shifted_arr;
    }

    // inverse mixColumns step for decryption
    public static byte[][] invMixColumns(byte[][] state) {
        byte[][] result = new byte[4][4];
        for (int col = 0; col < state[0].length; col++) {
            result[0][col] = (byte) (GMul((byte)0x0e, state[0][col]) ^ GMul((byte)0x0b, state[1][col]) ^ GMul((byte)0x0d, state[2][col]) ^ GMul((byte)0x09, state[3][col]));
            result[1][col] = (byte) (GMul((byte)0x09, state[0][col]) ^ GMul((byte)0x0e, state[1][col]) ^ GMul((byte)0x0b, state[2][col]) ^ GMul((byte)0x0d, state[3][col]));
            result[2][col] = (byte) (GMul((byte)0x0d, state[0][col]) ^ GMul((byte)0x09, state[1][col]) ^ GMul((byte)0x0e, state[2][col]) ^ GMul((byte)0x0b, state[3][col]));
            result[3][col] = (byte) (GMul((byte)0x0b, state[0][col]) ^ GMul((byte)0x0d, state[1][col]) ^ GMul((byte)0x09, state[2][col]) ^ GMul((byte)0x0e, state[3][col]));
        }
        return result;
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
