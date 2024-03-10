package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import utilities.AESImplementation;


public class AESIntegrationTest {
    final static int NUM_ROUNDS_KEY_128 = 10;
    final static String key = "c5e2c94cc80e257a0f450e1c54da0d7d";
    @Test
    public void encryptionTest() {
        String plainText = "4b5cc4be68b7cbed982e228e299b8d4a";
        String cipherText = AESImplementation.encrypt(plainText, key, NUM_ROUNDS_KEY_128);
        String expectedCipherText = "1e5196d4cf49473df67c13290bb601af";
        assertEquals(expectedCipherText, cipherText);
    }

    @Test
    public void decryptionTest() {
        
    }
}