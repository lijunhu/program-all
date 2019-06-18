package program.tiger.sword.common.utils;

import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author junhu.li
 * @ClassName AesUtil
 * @Description TODO
 * @date 2019-06-1716:10
 * @Version 1.0.0
 */
public class AesUtil {
    public static final byte[] ENCRYPT_IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public static String encrypt(String text, String password) {
        try {
            password = convertTo16Key(password);
            byte[] cipher = password.getBytes();
            PaddedBufferedBlockCipher engine = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
            engine.init(true, new ParametersWithIV(new KeyParameter(cipher), ENCRYPT_IV));
            byte[] content = text.getBytes("utf-8");
            byte[] enc = new byte[engine.getOutputSize(content.length)];
            int size1 = engine.processBytes(content, 0, content.length, enc, 0);
            int size2 = engine.doFinal(enc, size1);
            byte[] encryptedContent = new byte[size1 + size2];
            System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
            return Base64Util.encode(encryptedContent);
        } catch (Exception e) {
        }

        return null;
    }

    private static String convertTo16Key(String entryKey) {
        if ("" != entryKey || null != entryKey) {
            if (entryKey.length() == 16) {
                return entryKey;
            }
            if (entryKey.length() > 16)
                return entryKey.subSequence(0, 16).toString();
            if (entryKey.length() < 16) {
                return convet2Substi16Byte(entryKey);
            }
        }
        return null;
    }

    private static String convet2Substi16Byte(String key) {
        StringBuffer keyBuffer = new StringBuffer(key);
        for (int i = 0; i < 16 - key.length(); ++i) {
            keyBuffer.append("0");
        }
        return keyBuffer.toString();
    }

}
