package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import utilities.AESImplementation;

public class AESUnitTest {

    //Test Encryption methods
    @Test
    public void subBytesTest() {
        byte[][] input = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        
        byte[][] expected = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xbe, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };

        byte[][] output = AESImplementation.subBytes(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    @Test
    public void shiftRowsTest() {
        byte[][] input = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        
        byte[][] expected = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x5a, (byte)0x31, (byte)0x37, (byte)0x43},
            {(byte)0x98, (byte)0x07, (byte)0xf6, (byte)0x30},
            {(byte)0x34, (byte)0xa8, (byte)0x8d, (byte)0xa2}
        };

        byte[][] output = AESImplementation.shiftRows(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    @Test
    public void mixColumnsTest() {
        byte[][] input = {
            {(byte)0xdb, (byte)0xf2, (byte)0x01, (byte)0x2d},
            {(byte)0x13, (byte)0x0a, (byte)0x01, (byte)0x26},
            {(byte)0x53, (byte)0x22, (byte)0x01, (byte)0x31},
            {(byte)0x45, (byte)0x5c, (byte)0x01, (byte)0x4c}
        };

        byte[][] expected = {
            {(byte)0x8e, (byte)0x9f, (byte)0x01, (byte)0x4d},
            {(byte)0x4d, (byte)0xdc, (byte)0x01, (byte)0x7e},
            {(byte)0xa1, (byte)0x58, (byte)0x01, (byte)0xbd},
            {(byte)0xbc, (byte)0x9d, (byte)0x01, (byte)0xf8}
        };

        byte[][] outputArr = AESImplementation.mixColumns(input);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(outputArr[i][j]);
                assertEquals(outputArr[i][j], expected[i][j]);
            }
        }
    } 

    // NEED TO FIX THIS TEST
    // @Test
    // public void addRoundKeyTest() {
    //     byte[][] input = {
    //         {(byte)0xdb, (byte)0xf2, (byte)0x01, (byte)0x2d},
    //         {(byte)0x13, (byte)0x0a, (byte)0x01, (byte)0x26},
    //         {(byte)0x53, (byte)0x22, (byte)0x01, (byte)0x31},
    //         {(byte)0x45, (byte)0x5c, (byte)0x01, (byte)0x4c}
    //     };

    //     byte[][] key = {
    //         {(byte)0x8e, (byte)0x9f, (byte)0x01, (byte)0x4d},
    //         {(byte)0x4d, (byte)0xdc, (byte)0x01, (byte)0x7e},
    //         {(byte)0xa1, (byte)0x58, (byte)0x01, (byte)0xbd},
    //         {(byte)0xbc, (byte)0x9d, (byte)0x01, (byte)0xf8}
    //     };
    //     \\NEED TO COME BACK HERE AND FIX
    //     byte[][] expected = {
    //         ????????????????????????????
    //     };

    //     byte[][] outputArr = AESImplementation.addRoundKey(input, key);

    //     for (int i = 0; i < 4; i++) {
    //         for (int j = 0; j < 4; j++) {
    //             System.out.println(outputArr[i][j]);
    //             assertEquals(outputArr[i][j], expected[i][j]);
    //         }
    //     }
    // } 

    //Test Decryption methods
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

        byte[][] output = AESImplementation.invSubBytes(input);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    @Test
    public void inverseShiftRowsTest() {
        byte[][] input = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x43, (byte)0x5a, (byte)0x31, (byte)0x37},
            {(byte)0xf6, (byte)0x30, (byte)0x98, (byte)0x07},
            {(byte)0xa8, (byte)0x8d, (byte)0xa2, (byte)0x34}
        };
        
        byte[][] expected = {
            {(byte)0x32, (byte)0x88, (byte)0x31, (byte)0xe0},
            {(byte)0x37, (byte)0x43, (byte)0x5a, (byte)0x31},
            {(byte)0x98, (byte)0x07, (byte)0xf6, (byte)0x30},
            {(byte)0x8d, (byte)0xa2, (byte)0x34, (byte)0xa8}
        };

        byte[][] output = AESImplementation.invShiftRows(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], output[i][j]);
            }
        }
    }

    @Test
    public void invMixColumns() {
        byte[][] input = {
            {(byte)0x8e, (byte)0x9f, (byte)0x01, (byte)0x4d},
            {(byte)0x4d, (byte)0xdc, (byte)0x01, (byte)0x7e},
            {(byte)0xa1, (byte)0x58, (byte)0x01, (byte)0xbd},
            {(byte)0xbc, (byte)0x9d, (byte)0x01, (byte)0xf8}
        };

        byte[][] expected = {
            {(byte)0xdb, (byte)0xf2, (byte)0x01, (byte)0x2d},
            {(byte)0x13, (byte)0x0a, (byte)0x01, (byte)0x26},
            {(byte)0x53, (byte)0x22, (byte)0x01, (byte)0x31},
            {(byte)0x45, (byte)0x5c, (byte)0x01, (byte)0x4c}
        };

        byte[][] outputArr = AESImplementation.invMixColumns(input);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(outputArr[i][j]);
                assertEquals(outputArr[i][j], expected[i][j]);
            }
        }
    } 

    // Test Helper methods
    @Test
    public void getByteFromSBoxTest() {
        byte input = (byte) 1;
        byte output = (byte) 0x7c;
        assertEquals(output, AESImplementation.getByteFromBox(input, false));
        assertEquals(input, AESImplementation.getByteFromBox(output, true));
    }

    @Test
    public void shiftTest() {
        byte[] input = {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1};
        byte[] expected = {(byte)0xc7, (byte)0xe1, (byte)0x23, (byte)0xc4}; // for left
        byte[] output_left = AESImplementation.shift(input, 2, true);
        byte[] output_right = AESImplementation.shift(output_left, 2, false);

        //Test shift left
        for (int i = 0; i < output_left.length; i++) {
            assertEquals(expected[i], output_left[i]);
        }

        //Test shift right

        for (int i = 0; i < output_right.length; i++) {
            assertEquals(input[i], output_right[i]);
        }
    }

    //NEED TO COMPLETE THIS TEST

    // @Test
    // public void GMulTest(){
    //     byte input_1 = ;
    //     byte input_2 = ; 
    //     byte expected = ;
    //     byte output =  AESImplementation.GMul(input_1, input_2);

    //     assertEquals(output, expected);
    // }

    @Test
    public void stringToByteArrayTest() {
        String input = "23c4c7e11ab3c79a420446c5c25d3a18";
        byte[][] expected = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xb3, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        byte[][] outputArr = AESImplementation.stringToByteArray(input);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(outputArr[i][j]);
                assertEquals(outputArr[i][j], expected[i][j]);
            }
        }
    }

    @Test
    public void byteArrayToStringTest() {
        byte[][] input = {
            {(byte)0x23, (byte)0xc4, (byte)0xc7, (byte)0xe1},
            {(byte)0x1a, (byte)0xb3, (byte)0xc7, (byte)0x9a},
            {(byte)0x42, (byte)0x04, (byte)0x46, (byte)0xc5},
            {(byte)0xc2, (byte)0x5d, (byte)0x3a, (byte)0x18}
        };
        String expected = "23c4c7e11ab3c79a420446c5c25d3a18";

        String output = AESImplementation.byteArrayToString(input);
        assertEquals(expected, output);
    }
}