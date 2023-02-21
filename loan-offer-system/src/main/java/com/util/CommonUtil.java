package com.util;

import java.util.Random;

public class CommonUtil {

    public static String generateRandomPasscode() {
        Random random = new Random();
        int len = 8;
        int lowerLimit = 97;
        int upperLimit = 122;
        return random.ints(lowerLimit, upperLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
