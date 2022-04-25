package dmzjapiexplore;

import java.security.MessageDigest;

public class MD5 {
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String byteArrayToHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(byteToHexString(b));
        }
        return stringBuffer.toString();
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:0:0x0000 */
    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: byte */
    /* JADX DEBUG: Multi-variable search result rejected for r3v1, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r3v5, resolved type: int */
    /* JADX WARN: Multi-variable type inference failed */
    private static String byteToHexString(byte b1) {
        short b = b1;
        if (b < 0) {
            b += 256;
        }
        int i = b / 16;
        try {
            return hexDigits[i] + hexDigits[b % 16];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println(i);
        }
        return "11";
    }

    public static String MD5Encode(String str) {
        String str2 = null;
        String str3 = new String(str);
        System.out.println("Before MD5: " + str3);
        try {
            return byteArrayToHexString(MessageDigest.getInstance("MD5").digest(str3.getBytes()));
        } catch (Exception unused) {
            str2 = str3;
        }
        return str2;
    }
}