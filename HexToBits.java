import java.math.BigInteger;

public class HexToBits {
    public static void main(String[] args) {
        String hexString = "7b2e9f8c5a6d3f10e57cb4aef906d2a4";
        
        int decimalValue = Integer.parseInt(hexString.toCharArray()[0], 16);
        System.out.println(decimalValue); 
    }
}