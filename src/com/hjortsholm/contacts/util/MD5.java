package com.hjortsholm.contacts.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * <h1>MD5</h1>
 * This is a helper class that simplifies creating a MD5 digest.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class MD5 {
    /**
     * Hashes passed string and returns it.
     *
     * @param data String data to hash.
     * @return Base64 MD5 digest string.
     */
    public String getDigest(String data) {
        String result = data;
        byte[] bytesOfMessage;
        try {
            bytesOfMessage = data.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = Base64.getEncoder().encodeToString(md.digest(bytesOfMessage)).replaceAll("/", "");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
