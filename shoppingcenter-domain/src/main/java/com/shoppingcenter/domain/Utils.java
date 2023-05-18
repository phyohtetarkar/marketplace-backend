package com.shoppingcenter.domain;

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
    
    public static String generateRandomNumber(int length) {
        String randomChars = "0123456789";
        int charLength = randomChars.length();
        String result = "";

        for (int i = 0; i < length; i++) {
            result += randomChars.charAt((int) Math.floor(Math.random() * charLength));
        }

        return result;
    }

    /**
     * @param prefix
     * @param checkExists
     * @return String array containing [0] for full slug and [1] for postfix code
     */
    public static String[] generateSlug(String prefix, Function<String, Boolean> checkExists) {
        var postfix = Utils.generateRandomCode(6);
        var result = prefix + "-" + postfix;

        return checkExists.apply(result) ? generateSlug(prefix, checkExists) : new String[] { result, postfix };
    }
    
    public static String convertToSlug(String value) {
    	if (value == null) {
    		return "";
    	}
    	
    	return value.toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }

    public static boolean equalsIgnoreCase(String value1, String value2) {
        if (value1 == null || value2 == null) {
            return false;
        }
        return value1.trim().toLowerCase().equals(value2.trim().toLowerCase());
    }

    public static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    public static boolean isPhoneNumber(String value) {
    	 var phoneRegex = "^(09)\\d{7,12}$";

         if (!Utils.hasText(value) || !value.matches(phoneRegex)) {
             return false;
         }
         
         return true;
    }

}
