package cn.throwx.canal.gule.example.ch4;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/26 20:52
 */
public enum AESUtils {

    /**
     * 单例
     */
    X;

    private static final String PWD = "1234567890123456";
    private static final String ALGORITHM = "AES";
    private static final String MODE = "AES/ECB/PKCS5Padding";

    public String encrypt(String content) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(PWD.getBytes(), ALGORITHM);
            Cipher encipher = Cipher.getInstance(MODE);
            encipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] result = encipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(result);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String decrypt(String content) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(PWD.getBytes(), ALGORITHM);
            Cipher encipher = Cipher.getInstance(MODE);
            encipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decode = Base64.decodeBase64(content);
            byte[] result = encipher.doFinal(decode);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
