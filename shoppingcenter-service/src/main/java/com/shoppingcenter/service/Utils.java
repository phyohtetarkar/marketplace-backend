package com.shoppingcenter.service;

import java.util.function.Function;

public class Utils {

    public static int normalizePage(Integer page) {
        return page != null && page >= 0 ? page : 0;
    }

    public static String generateRandomCode(int length) {
        String randomChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        int charLength = randomChars.length();
        String result = "";

        for (int i = 0; i < length; i++) {
            result += randomChars.charAt((int) Math.floor(Math.random() * charLength));
        }

        return result;
    }

    public static String generateSlug(String prefix, Function<String, Boolean> checkExists) {
        String result = prefix + "-" + Utils.generateRandomCode(6);

        return checkExists.apply(result) ? generateSlug(prefix, checkExists) : result;
    }

    public static boolean equalIgnorecase(String value1, String value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.trim().toLowerCase().equals(value2.trim().toLowerCase());
    }

}
