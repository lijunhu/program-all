package program.tiger.sword.common;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author junhu.li
 * @ClassName crypt
 * @Description TODO
 * @date 2020/2/2519:09
 * @Version 1.0.0
 */
public class crypt {

    private static Base64.Decoder decoder = Base64.getDecoder();


    public static String decryptUserData(String encryptDataB64, String sessionKeyB64, String ivB64) {
        return new String(
                decryptOfDiyIV(
                        decoder.decode(encryptDataB64),
                        decoder.decode(sessionKeyB64),
                        decoder.decode(ivB64)
                )
        );
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return 解密后的字节数组
     */
    private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    private static void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //解密方法（解密key：0907133912034225）

    /**
     * @param str 待解密内容
     * @param key 秘钥
     * @return
     */
    public static String decode(String str, String key) {
        try {
            if (str == null || key == null) return null;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
            byte[] bytes = decoder.decode(str);
            bytes = cipher.doFinal(bytes);
            return new String(bytes, "utf-8");
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) throws Exception {

        String aaa = decode("NdOaSwoY0HKpKrPxHeGUDUN5jr/uJQHK3HVgyttASc4=", "0907133912034225");

        String encryptData = "32qEDGLYnZotiOceVCOMvZwvW9XMX1gIVww4fnhGKTO+FLTJYMXXCe9g3ag2/ubbhWomw1ImDpw86YF4bgKY/8nfmRM4ifdYu071U2utz+GgaAYeCZzypnrB4ptNvMxrykix6rMA7rSdkfjoaLefR1eJZOJ7pZiEB6WC/LThIcTwb1ay7zD9/8U5iSZBCu81Zhc6aaY8pM/Ywx+7lDhuIubVMd+fW+ixtwzZ+rTeUMfDN66N2H5hXVQ+M9jz27oI1zfgyVg1qrd5/5ug6/tux+akhTk4bgk+Dw4MQ2joI8AKEiTuecq2YBhNYWOTwjLqfhHc+HouMjvpU71+SzHqUmtAHhqXcQu/Zq3QEVUEps3TV7KRytsbS9nwDU/EYF4GrJaPIO0Mx0OcnGKsMsK8sFTb4dqb1adRtn8BYxyVogEmGAMRAZ5FhwkkYo/xNjgZqZHi0EYNramQWiznmrjRLw==";
        String sessionKey = "5Z5unsDHVPWrpeJSQXwhFw==";
        String iv = "xWQpEx6fOH9mVGkaN3/uaA==";
        String json = decryptUserData(encryptData, sessionKey, iv);
        System.out.println(json);
    }
}
