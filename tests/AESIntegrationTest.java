package tests;
import static org.junit.Assert.*;
import org.junit.Test;
import utilities.AESImplementation;


public class AESIntegrationTest {
    final static String key = "5468617473206D79204B756E67204675";
    @Test
    public void encryptionTest() {
        String plainText = "54776F204F6E65204E696E652054776F";
        
        String cipherText = AESImplementation.encrypt(plainText, key);
        String expectedCipherText = "29c3505f571420f6402299b31a02d73a";
        assertEquals(expectedCipherText, cipherText);
    }

    @Test
    public void decryptionTest() {
        String plainText = "29c3505f571420f6402299b31a02d73a";
        String cipherText = AESImplementation.encrypt(plainText, key);
        String expectedCipherText = "54776F204F6E65204E696E652054776F";
        assertEquals(expectedCipherText, cipherText);
    }
}