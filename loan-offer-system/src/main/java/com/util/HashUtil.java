package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class HashUtil {

    static Logger logger = Logger.getLogger(HashUtil.class.getName());
    public static String getHashCode(String str) {
        String hashCode = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            hashCode = hexString.toString();
        }catch (NoSuchAlgorithmException exception){
            logger.info("An error occurred in getHashCode :"+ exception);
        }
        return hashCode;
    }

}
