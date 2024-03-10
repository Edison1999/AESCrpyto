package utilities;

public class AESImplementation implements AESImplementationInterface { 

    // KEY EXPANSION
    public static byte[][][] keyExpansion(byte[][] key) {
        byte[][][] expandedKey = new byte[11][][];
        // the first expanded key is the original key
        expandedKey[0] = key;
        byte[][] oldKey = new byte[4][4];
        for (int round = 1; round < 11; round++) {  // obtain 10 expanded keys
            oldKey = expandedKey[round-1];
            expandedKey[round] = generateNewKey(oldKey, round);
        }
        return expandedKey;
    }

    public static byte[][] generateNewKey(byte[][] oldKey, int round) {
        byte[][] newKey = new byte[4][4];
        byte[] temp = new byte[4];
        byte current = 0;
        // 1. get the last column of the last key
        for (int i = 0; i < 4; i++) {
            temp[i] = oldKey[i][3];
        }
        
        // 2. get temp column from keyExpansionCore
        temp = keyExpansionCore(temp, round);

        // 3. xor with the last key, column by column
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                current = xorByte(oldKey[j][i], temp[j]);
                temp[j] = current;
                newKey[j][i] = current;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
            }
        }
        return newKey;
    }

    public static byte[] keyExpansionCore(byte[] tempKey, int round) {
        byte[] result = new byte[4];

        // 1. rotate left
        result[0] = tempKey[1];
        result[1] = tempKey[2];
        result[2] = tempKey[3];
        result[3] = tempKey[0];
        
        // 2. S-BOX
        for (int i = 0; i < 4; i++) {
            result[i] = getByteFromBox(result[i], false);
        }

        // 3. RCon
        result[0] = xorByte(result[0], RCON[round-1]);
        
        return result;
    }

    public static byte[] xorByteArray(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = xorByte(arr1[i], arr2[i]);
        }
        return result;
    }

    public static byte xorByte(byte a, byte b) {
        int temp = ((int) a) ^ ((int) b);
        return (byte) temp;
    }


    // ENCRYPTION
    // Encrypts the string s
    public static String encrypt(String s, String k, int rounds) {
        byte[][] state = stringToByteArray(s);
        byte[][] key = stringToByteArray(k);
        state = addRoundKey(state, key);
        for (int i = 0; i < rounds-1; i++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, key);
        }
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, key);
        // Convert the state array to a string and return it as output
        return byteArrayToString(state);
    }

    // addRoundKey step for encryption/decryption
    public static byte[][] addRoundKey(byte[][] state, byte[][] key) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int temp = ((int) state[i][j]) ^ ((int) key[i][j]);
                result[i][j] = (byte) temp;
            }
        }
        return result;
    }

    // subBytes step for encryption
    public static byte[][] subBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                result[i][j] = getByteFromBox(state[i][j], false);
            }
        }
        return result;
    }

     // shiftRows step for encryption/decryption
     public static byte[][] shiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0]=state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shift(state[i], i, true);
        }
        return result;
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

    // DECRYPTION
    // Decrypts the String s
    public static String decrypt(String s, String k, int rounds) {
        byte[][] state = stringToByteArray(s);
        byte[][]  key = stringToByteArray(k);
        state = addRoundKey(state, key);
        state = shiftRows(state);
        state = subBytes(state);
        for (int i = 0; i < rounds-1; i++) {
            state = addRoundKey(state, key);
            state = invMixColumns(state);
            state = invShiftRows(state);
            state = invSubBytes(state);
        }
        state = addRoundKey(state, key);
        // Convert the state array to a string and return it as output
        return byteArrayToString(state);
    }

    // inverse subBytes step for decryption
    public static byte[][] invSubBytes(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                result[i][j] = getByteFromBox(state[i][j], true);
            }
        }
        return result;
    }

    // inverse shiftRows step for decryption
    public static byte[][] invShiftRows(byte[][] state) {
        byte[][] result = new byte[state.length][state[0].length];
        result[0] = state[0];
        for (int i = 1; i < state.length; i++) {
            result[i] =  shift(state[i], i, false);
        }
        return result;
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

    // HELPER METHODS
    // Get the byte value associated wiht byte b from Forward S-box or Inverse S-box
    public static byte getByteFromBox(byte b, Boolean inverse) {
        int i = Byte.toUnsignedInt(b);
        return inverse ? INVERSE_S_BOX[i] : FORWARD_S_BOX[i];
    }
   
    // Helper shift array to the left by shift indices
    public static byte[] shift(byte[] bs, int shift, boolean left) {
        byte[] shifted_arr = new byte[bs.length];
        int shiftTo;
        int factor = left ? 1 : -1;
        for (int i = 0; i < bs.length; i++) {
            shiftTo = (i + shift*factor + 4) % 4;
            shifted_arr[i] = bs[shiftTo];
        }
        return shifted_arr;
    }

    // Galois Field (256) Multiplication of two Bytes for mixColumns
    public static byte GMul(byte a, byte b) {
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
                start = i * 2 + j * 8;
                temp = s.substring(start, start + 2);
                result[i][j] = hexStringToByte(temp);
            }
        }
        return result;
    }

    // convert a byte array arr to a string
    public static String byteArrayToString(byte[][] arr) {
        String temp = "";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                temp += byteToHexString(arr[j][i]);
            }
        }
        return temp;
    }

    // convert a hex string s to a byte
    private static byte hexStringToByte(String s) {
        int i = Integer.parseInt(s, 16);
        byte b = (byte) i;
        return b;
    }

    // convert a byte b to a hex string
    private static String byteToHexString(byte b) {
        int i = b & 0xff;
        String s = Integer.toHexString(i);
        if (s.length() % 2 == 1) { // if only one digit, pad with zero
            s = "0" + s;
        }
        return s;
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