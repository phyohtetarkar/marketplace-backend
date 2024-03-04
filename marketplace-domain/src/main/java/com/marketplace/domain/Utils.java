package com.marketplace.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.util.StringUtils;

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
     * @return String
     */
    public static String generateSlug(String prefix, Function<String, Boolean> checkExists) {
        //var postfix = Utils.generateRandomCode(6);
        var result = prefix;
        
        int count = 1;
        
        while (checkExists.apply(result)) {
        	result = String.format("%s-%d", prefix, count);
        	count += 1;
        }

        return result;
    }
    
    public static String convertToSlug(String value) {
    	if (value == null) {
    		return "";
    	}
    	
    	return value.trim().toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
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
    
    public static boolean isEmailAddress(String value) {
    	var emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    	if (!StringUtils.hasText(value) || !value.matches(emailRegex)) {
            return false;
        }
    	return true;
    }
    
    public static String getFileExtension(String fileName) {
    	int lastIndex = fileName.lastIndexOf(".");
    	return fileName.substring(lastIndex);
    }
    
    public static String getCurrentDateTimeFormatted() {
    	var dateTime = LocalDateTime.now(ZoneOffset.UTC);
		var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		return dateTime.format(dateTimeFormatter);
    }

}
