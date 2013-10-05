/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013,2014 by Peter Pilgrim, Addiscombe, Surrey, XeNoNiQUe UK
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package je7hb.jaxrs.basic;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * The type AESCipher
 *
 * @author Peter Pilgrim (peter)
 */
public class AESCipher {

    private final KeyGenerator keyGen;
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public AESCipher( String passwordText, final byte[] salt ) throws
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException,
            InvalidKeyException, InvalidKeySpecException {
        keyGen = KeyGenerator.getInstance("AES");

        final char[] password = passwordText.toCharArray();

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey aesKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        // Encrypt cipher
        encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
        encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);

        // Decrypt cipher
        decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
    }

    public Cipher getEncryptCipher() {
        return encryptCipher;
    }

    public Cipher getDecryptCipher() {
        return decryptCipher;
    }

    public byte[] encrypt( String plainText ) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
        try {
            cipherOutputStream.write(plainText.getBytes());
            cipherOutputStream.flush();
            cipherOutputStream.close();
            return outputStream.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public String decrypt( byte[] cipherText ) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(cipherText);
        CipherInputStream cipherInputStream = null;
        try {
            cipherInputStream = new CipherInputStream(inputStream, decryptCipher);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
                output.write(buf, 0, bytesRead);
            }
            cipherInputStream.close();
            return  new String(output.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

}
