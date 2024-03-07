// import static org.junit.Assert.*;
// import org.junit.Test;

public class AESTest {
    
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
