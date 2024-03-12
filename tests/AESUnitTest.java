package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import utilities.AESImplementation;

public class AESUnitTest {

    
    @Test
    public void keyExpansionTest() {
        String key = "5468617473206D79204B756E67204675";
        byte [][] byte_key = AESImplementation.stringToByteArray(key);
        byte[][][] res = AESImplementation.keyExpansion(byte_key);
        String [] expected = {
            "5468617473206d79204b756e67204675",
            "e232fcf191129188b159e4e6d679a293",
            "56082007c71ab18f76435569a03af7fa",
            "d2600de7157abc686339e901c3031efb",
            "a11202c9b468bea1d75157a01452495b",
            "b1293b3305418592d210d232c6429b69",
            "bd3dc287b87c47156a6c9527ac2e0e4e",
            "cc96ed1674eaaa031e863f24b2a8316a",
            "8e51ef21fabb4522e43d7a0656954b6c",
            "bfe2bf904559fab2a16480b4f7f1cbd8",
            "28fddef86da4244accc0a4fe3b316f26"
        };
        for (int i = 0; i < res.length; i++){
            assertEquals(expected[i], AESImplementation.byteArrayToString(res[i]));
        }
    }

    @Test
    public void keyExpansionCoreTest() {
        byte[] tempKey = {(byte)0x13, (byte)0xaa, (byte)0x54, (byte)0x87};
        int round = 1;
        byte[] outputTempKey = AESImplementation.keyExpansionCore(tempKey, round);
        byte[] expected = {(byte)0xad, (byte)0x20, (byte)0x17, (byte)0x7d};
        assertArrayEquals(expected, outputTempKey);
    }

    @Test
    public void generateNewKeyTest() {
        byte[][] oldKey = {
            {(byte)0x24, (byte)0x34, (byte)0x31, (byte)0x13},
            {(byte)0x75, (byte)0x75, (byte)0xe2, (byte)0xaa},
            {(byte)0xa2, (byte)0x56, (byte)0x12, (byte)0x54},
            {(byte)0xb3, (byte)0x88, (byte)0x00, (byte)0x87}
        };
        byte[][] expected = {
            {(byte)0x89, (byte)0xbd, (byte)0x8c, (byte)0x9f},
            {(byte)0x55, (byte)0x20, (byte)0xc2, (byte)0x68},
            {(byte)0xb5, (byte)0xe3, (byte)0xf1, (byte)0xa5},
            {(byte)0xce, (byte)0x46, (byte)0x46, (byte)0xc1}
        };
        byte[][] output = AESImplementation.generateNewKey(oldKey, 1);
        assertArrayEquals(expected, output);
    }

    /**
     * Test Encryption methods
     */
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
        assertArrayEquals(expected, output);
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
        assertArrayEquals(expected, output);
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
        assertArrayEquals(expected, outputArr);
    } 

    @Test
    public void addRoundKeyTest() {
        String inputStr = "0dfcb6ccd6adf66a866dec755f77e001";
        String keyStr = "efe98857bf89a365ab9dd37dc58db928";
        byte[][] inputArr = AESImplementation.stringToByteArray(inputStr);
        byte[][] keyArr = AESImplementation.stringToByteArray(keyStr);
        String expectedStr = "e2153e9b6924550f2df03f089afa5929";
        byte[][] expectedArr = AESImplementation.stringToByteArray(expectedStr);
        // compute result array from addRoundKey method
        byte[][] outputArr = AESImplementation.addRoundKey(inputArr, keyArr);
        assertArrayEquals(expectedArr, outputArr);
    } 

    /**
     * Test Decryption methods
     */
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
        assertArrayEquals(expected, output);
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
        assertArrayEquals(expected, output);
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
        assertArrayEquals(expected, outputArr);
    } 

    /**
     * Test Helper methods
     */
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
        assertArrayEquals(expected, output_left);

        //Test shift right
        assertArrayEquals(input, output_right);
    }

    @Test
    public void GMulTest() {
        // hex: 57 * 80 = 38
        byte input_1 = (byte)0x57;
        byte input_2 = (byte)0x80; 
        byte expected = (byte)0x38;
        byte output =  AESImplementation.GMul(input_1, input_2);
        assertEquals(output, expected);
    }

    @Test
    public void stringToByteArrayTest() {
        String input = "23c4c7e11ab3c79a420446c5c25d3a18";
        byte[][] expected = {
            {(byte)0x23, (byte)0x1a, (byte)0x42, (byte)0xc2},
            {(byte)0xc4, (byte)0xb3, (byte)0x04, (byte)0x5d},
            {(byte)0xc7, (byte)0xc7, (byte)0x46, (byte)0x3a},
            {(byte)0xe1, (byte)0x9a, (byte)0xc5, (byte)0x18}
        };
        byte[][] outputArr = AESImplementation.stringToByteArray(input);
        assertArrayEquals(expected, outputArr);
    }

    @Test
    public void byteArrayToStringTest() {
        byte[][] input = {
            {(byte)0x23, (byte)0x1a, (byte)0x42, (byte)0xc2},
            {(byte)0xc4, (byte)0xb3, (byte)0x04, (byte)0x5d},
            {(byte)0xc7, (byte)0xc7, (byte)0x46, (byte)0x3a},
            {(byte)0xe1, (byte)0x9a, (byte)0xc5, (byte)0x18}
        };
        String expected = "23c4c7e11ab3c79a420446c5c25d3a18";

        String output = AESImplementation.byteArrayToString(input);
        assertEquals(expected, output);
    }
}