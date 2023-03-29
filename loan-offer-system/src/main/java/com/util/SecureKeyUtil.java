package com.util;

import java.util.UUID;
import java.util.logging.Logger;

public class SecureKeyUtil {

    static Logger logger = Logger.getLogger(HashUtil.class.getName());
    public static String getSecureRandomKey() {
        try {
            UUID uuid = UUID.randomUUID();
            return uuid.toString();
        }catch (Exception exception){
            logger.info("An error occurred in getHashCode :"+ exception);
            return null;
        }
    }

}
