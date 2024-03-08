public class HexToByte {

    public static void hexStringToByte() {
        String s = "ff";
        int i = Integer.parseInt(s, 16);
        byte b = (byte) i;
        System.out.println(Byte.toUnsignedInt(b));
    }

    public static void byteToHexString() {
        byte b = (byte) 255;
        int i = b & 0xff;
        String s = Integer.toHexString(i);
        System.out.println(s);
    }

    public static void byteToInt() {
        byte b = (byte)0xff;
        int i = Byte.toUnsignedInt(b);
        System.out.println(i);
    }

    public static void intToByte() {
        int i = 255;
        byte b = (byte) i;
        System.out.println(Byte.toUnsignedInt(b));
    }

    public static void main(String[] args) {
        hexStringToByte();
        byteToHexString();
        byteToInt();
        intToByte();
    }
}
