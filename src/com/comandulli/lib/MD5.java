package com.comandulli.lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 encodes any String into a one way encrypted value.
 *
 * @author <a href="mailto:caioa.comandulli@gmail.com">Caio Comandulli</a>
 * @since 1.0
 */
public class MD5 {
    /**
     * Encode the following string in MD5.
     *
     * @param value the string
     * @return the encoded string.
     */
    public static String encode(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(value.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString(anArray & 0xFF | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
