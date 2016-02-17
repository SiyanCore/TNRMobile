package com.siyanmo.tnrmobile.WebAPI;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by Administrator on 2016-02-17.
 */
public class CryptoUtil {
    //public static final Logger LOG = Logger.getLogger(CryptoUtil.class);

    private Cipher cipher = null;

    private SecretKey key = null;

    // This variable holds a string based on which a unique key will be generated
    private static final String SECRET_PHRASE = "Pbs5pr@se";

    // Charset will be used to convert between String and ByteArray
    private static final String CHARSET = "UTF8";

    // The algorithm to be used for encryption/decryption DES(Data Encryption Standard)
    private static final String ALGORITHM = "DES";
    byte[] keyBytes;
    byte[] initVectorBytes;
    public CryptoUtil()  {
        try {
            // generate a key from SecretKeyFactory
            DESKeySpec keySpec = new DESKeySpec(SECRET_PHRASE.getBytes(CHARSET));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            key = keyFactory.generateSecret(keySpec);
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (Exception e) {
           // LOG.error(e);
            //throw new DDICryptoException(e);
        }
    }


    /**
     * This method takes a plain text string and returns encrypted string using DES algorithm
     * @param plainText
     * @return String
     */
    public String encrypt(String plainText) {
        String encryptedString = null;
        try {
            // initializes the cipher with a key.
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextAsUTF8 = plainText.getBytes(CHARSET);

            // decrypts data in a single-part or multi-part operation
            byte[] encryptedBytes = cipher.doFinal(plainTextAsUTF8);

            encryptedString = new BASE64Encoder().encode(encryptedBytes);
        } catch (Exception e) {
           // LOG.error(e);
            //throw new DDICryptoException(e);

        }
        return encryptedString;

    }

    /**
     * This method takes a plain text string and returns encrypted string using DES algorithm
     * @param encryptedString
     * @return

     */
    public String decrypt(String encryptedString)  {
        String decryptedString = null;
        try {
            byte[] decodedString = new BASE64Decoder().decodeBuffer(encryptedString);

            // initializes the cipher with a key.
            cipher.init(Cipher.DECRYPT_MODE, key);

            // decrypts data in a single-part or multi-part operation
            byte[] decryptedBytes = cipher.doFinal(decodedString);
            decryptedString = new String(decryptedBytes, CHARSET);
        } catch (Exception e) {
           // LOG.error(e);
           // throw new DDICryptoException(e);
        }
        return decryptedString;
    }
}
