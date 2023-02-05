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
        String result = prefix + "-" + Utils.generateRandomCode(5);

        return checkExists.apply(result) ? generateSlug(prefix, checkExists) : result;
    }

}
