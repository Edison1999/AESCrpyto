import static org.junit.Assert.*;
import org.junit.Test;

public class AESTest {

    // test the getByteFromSBox and getByteFromInvSBox method
    @Test
    public void getByteFromSBoxTest() {
        byte input = (byte) 1;
        byte output = (byte) 0x7c;
        assertEquals(output, AES.getByteFromSBox(input));
        assertEquals(input, AES.getByteFromInvSBox(output));
    }

    // test the subBytes method
    @Test
    public void subBytesTest() {
        // Input state matrix
        byte[][] input = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        
        // Testing subbytes and invSubBytes
        byte[][] expected = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xbe, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };

        // the result array matches the expected output
        byte[][] output = AES.subBytes(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    // test the invSubBytes method
    @Test
    public void InvSubBytesTest() {
        byte[][] input = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xbe, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        byte[][] expected = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        byte[][] output = AES.invSubBytes(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    // test the shiftLeft method
    @Test
    public void shiftRightTest() {
        byte[] input = {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1};
        byte[] expectedOutput = {(byte)0xc7, (byte)0xe1, (byte)0x23, (byte)0xc4};
        byte[] output = AES.shiftRight(input, 2);
        for (int i = 0; i < output.length; i++) {
            assertEquals(expectedOutput[i], output[i]);
        }
    }

    // test the shiftLeft method
    @Test
    public void shiftLeftTest() {
        byte[] input = {(byte)0xc7, (byte)0xe1, (byte)0x23, (byte)0xc4};
        byte[] expectedOutput= {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1};
        byte[] output = AES.shiftLeft(input, 2);
        for (int i = 0; i < output.length; i++) {
            assertEquals(expectedOutput[i], output[i]);
        }
    }

    // test the stringToByteArray method
    @Test
    public void stringToByteArrayTest() {
        String input = "23c4c7e11ab3c79a420446c5c25d3a18";
        byte[][] expected = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xb3, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        byte[][] outputArr = AES.stringToByteArray(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(outputArr[i][j]);
                assertEquals(outputArr[i][j], expected[i][j]);
            }
        }
    }

    // test the stringToByteArray method
    @Test
    public void byteArrayToStringTest() {
        byte[][] input = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xb3, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        String expected = "23c4c7e11ab3c79a420446c5c25d3a18";

        String output = AES.byteArrayToString(input);
        assertEquals(expected, output);
    }

    // @Test
    public static void main(String[] args) {
        AES aesImpl = new AES();
        
        // Input state matrix
        byte[][] state = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        
        // Testing subbytes and invSubBytes
        byte[][] expected_subbytes = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xbe, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        byte[][] result_subbytes = AES.subBytes(state);
        System.out.println("Result testing subbytes: ");
        System.out.println("Result:");
        printToHex(result_subbytes);
        System.out.println("Expected:");
        printToHex(expected_subbytes);


        byte[][] result_invSubBytes = AES.invSubBytes(result_subbytes);
        System.out.println("Result testing invSubBytes: ");
        System.out.println("Result:");
        printToHex(result_invSubBytes);
        System.out.println("Expected:");
        printToHex(state);

        System.out.println("****************************************************");

        // Testing shiftRows and invShiftRows
        byte[][] expected_shiftrows = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x5a, (byte)0x31, (byte)0x37, (byte)0x43},
            {(byte)0x98, (byte)0x07, (byte)0xf6, (byte)0x30},
            {(byte)0x34, (byte)0xa8, (byte)0x8d, (byte)0xa2}
        };
        byte[][] result_shiftrows = AES.shiftRows(state);
        System.out.println("Result testing shiftRows: ");
        System.out.println("Result:");
        printToHex(result_shiftrows);
        System.out.println("Expected:");
        printToHex(expected_shiftrows);

        byte[][] result_invShiftRows = AES.invShiftRows(result_shiftrows);
        System.out.println("Result testing invShiftRows: ");
        System.out.println("Result:");
        printToHex(result_invShiftRows);
        System.out.println("Expected:");
        printToHex(state);

        System.out.println("****************************************************");


    }
    public static void printToHex(byte[][] arr){
        for (byte[] temp : arr){
            for (byte inn: temp){
                System.out.printf("%02X ", inn);
            }
            System.out.println();
        }
    }
}
