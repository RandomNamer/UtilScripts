package dmzjapiexplore;

import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class RSAUtils {
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    private static int DECRYPT_BLOCK = 0;
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();
    private static final String ECB_PADDING = "RSA/ECB/PKCS1Padding";
    private static int ENCRYPT_BLOCK = 0;
    private static int KEYSIZE = 2048;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int MAX_ENCRYPT_BLOCK = 117;
    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK8nNR1lTnIfIes6oRWJNj3mB6OssDGx0uGMpgpbVCpf6+VwnuI2stmhZNoQcM417Iz7WqlPzbUmu9R4dEKmLGEEqOhOdVaeh9Xk2IPPjqIu5TbkLZRxkY3dJM1htbz57d/roesJLkZXqssfG5EJauNc+RcABTfLb4IiFjSMlTsnAgMBAAECgYEAiz/pi2hKOJKlvcTL4jpHJGjn8+lL3wZX+LeAHkXDoTjHa47g0knYYQteCbv+YwMeAGupBWiLy5RyyhXFoGNKbbnvftMYK56hH+iqxjtDLnjSDKWnhcB7089sNKaEM9Ilil6uxWMrMMBH9v2PLdYsqMBHqPutKu/SigeGPeiB7VECQQDizVlNv67go99QAIv2n/ga4e0wLizVuaNBXE88AdOnaZ0LOTeniVEqvPtgUk63zbjl0P/pzQzyjitwe6HoCAIpAkEAxbOtnCm1uKEp5HsNaXEJTwE7WQf7PrLD4+BpGtNKkgja6f6F4ld4QZ2TQ6qvsCizSGJrjOpNdjVGJ7bgYMcczwJBALvJWPLmDi7ToFfGTB0EsNHZVKE66kZ/8Stx+ezueke4S556XplqOflQBjbnj2PigwBN/0afT+QZUOBOjWzoDJkCQClzo+oDQMvGVs9GEajS/32mJ3hiWQZrWvEzgzYRqSf3XVcEe7PaXSd8z3y3lACeeACsShqQoc8wGlaHXIJOHTcCQQCZw5127ZGs8ZDTSrogrH73Kw/HvX55wGAeirKYcv28eauveCG7iyFR0PFB/P/EDZnyb+ifvyEFlucPUI0+Y87F";
    private static int RESERVE_BYTES = 11;
    private static final String RSA = "RSA";
    static {
        int i = 2048 / 8;
        DECRYPT_BLOCK = i;
        ENCRYPT_BLOCK = i - 11;
    }

    public static KeyPair generateKeyPair(int i) {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance(RSA);
            instance.initialize(i);
            return instance.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptWithPublicKey(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(1, getPublicKey(bArr2));
        return instance.doFinal(bArr);
    }

    public static byte[] decryptWithPublicKey(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(2, getPublicKey(bArr2));
        return instance.doFinal(bArr);
    }

    public static byte[] encryptWithPrivateKey(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(1, getPrivateKey(bArr2));
        return instance.doFinal(bArr);
    }

    public static byte[] decryptWithPrivateKey(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(2, getPrivateKey(bArr2));
        return instance.doFinal(bArr);
    }

    public static byte[] decryptByPrivateKey(byte[] bArr, byte[] bArr2) throws Exception {
        PrivateKey generatePrivate = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(2, generatePrivate);
        return instance.doFinal(bArr);
    }

    public static PublicKey getPublicKey(byte[] bArr) throws Exception {
        return KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(bArr));
    }

    public static PrivateKey getPrivateKey(byte[] bArr) throws Exception {
        PrivateKey key = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bArr));

        System.out.println(key);
        return key;
    }

    public static byte[] encryptWithPublicKeyBlock(byte[] bArr, byte[] bArr2) throws Exception {
        int length = bArr.length;
        int i = ENCRYPT_BLOCK;
        int i2 = length / i;
        if (bArr.length % i != 0) {
            i2++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 * ENCRYPT_BLOCK);
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(1, getPublicKey(bArr2));
        for (int i3 = 0; i3 < bArr.length; i3 += ENCRYPT_BLOCK) {
            int length2 = bArr.length - i3;
            int i4 = ENCRYPT_BLOCK;
            if (length2 > i4) {
                length2 = i4;
            }
            byteArrayOutputStream.write(instance.doFinal(bArr, i3, length2));
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] encryptWithPrivateKeyBlock(byte[] bArr, byte[] bArr2) throws Exception {
        int length = bArr.length;
        int i = ENCRYPT_BLOCK;
        int i2 = length / i;
        if (bArr.length % i != 0) {
            i2++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 * ENCRYPT_BLOCK);
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(1, getPrivateKey(bArr2));
        for (int i3 = 0; i3 < bArr.length; i3 += ENCRYPT_BLOCK) {
            int length2 = bArr.length - i3;
            int i4 = ENCRYPT_BLOCK;
            if (length2 > i4) {
                length2 = i4;
            }
            byteArrayOutputStream.write(instance.doFinal(bArr, i3, length2));
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decryptWithPublicKeyBlock(byte[] bArr, byte[] bArr2) throws Exception {
        int length = bArr.length;
        int i = DECRYPT_BLOCK;
        int i2 = length / i;
        if (bArr.length % i != 0) {
            i2++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 * DECRYPT_BLOCK);
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(2, getPublicKey(bArr2));
        for (int i3 = 0; i3 < bArr.length; i3 += DECRYPT_BLOCK) {
            int length2 = bArr.length - i3;
            int i4 = DECRYPT_BLOCK;
            if (length2 > i4) {
                length2 = i4;
            }
            byteArrayOutputStream.write(instance.doFinal(bArr, i3, length2));
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decryptWithPrivateKeyBlock2(byte[] bArr, byte[] bArr2) throws Exception {
        int length = bArr.length;
        int i = DECRYPT_BLOCK;
        int i2 = length / i;
        if (bArr.length % i != 0) {
            i2++;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 * DECRYPT_BLOCK);
        Cipher instance = Cipher.getInstance(ECB_PADDING);
        instance.init(2, getPrivateKey(bArr2));
        for (int i3 = 0; i3 < bArr.length; i3 += DECRYPT_BLOCK) {
            int length2 = bArr.length - i3;
            int i4 = DECRYPT_BLOCK;
            if (length2 > i4) {
                length2 = i4;
            }
            byteArrayOutputStream.write(instance.doFinal(bArr, i3, length2));
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decryptWithPrivateKeyBlock(String str) {
        byte[] bArr;
        try {
            byte[] decode = Base64.getDecoder().decode(str);
            byte[] decode2 = Base64.getDecoder().decode(PRIVATE_KEY);
            Cipher instance = Cipher.getInstance(ECB_PADDING);
            instance.init(2, getPrivateKey(decode2));
            int length = decode.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 > 0) {
                    if (i3 > 128) {
                        bArr = instance.doFinal(decode, i, 128);
                    } else {
                        bArr = instance.doFinal(decode, i, i3);
                    }
                    byteArrayOutputStream.write(bArr, 0, bArr.length);
                    i2++;
                    i = i2 * 128;
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] bArr, byte[] bArr2) {
        byte[] bArr3;
        try {
            Cipher instance = Cipher.getInstance(ECB_PADDING);
            instance.init(2, getPublicKey(bArr2));
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = length - i;
                if (i3 > 0) {
                    if (i3 > 128) {
                        bArr3 = instance.doFinal(bArr, i, 128);
                    } else {
                        bArr3 = instance.doFinal(bArr, i, i3);
                    }
                    byteArrayOutputStream.write(bArr3, 0, bArr3.length);
                    i2++;
                    i = i2 * 128;
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    return byteArray;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}