package com.marketplace.app.common;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Misc misc;

    private Image image;
    
    private Payment payment;
    
    private Firebase firebase;
    
    private SuperUser superUser;

    public String getImagePath() {
        return image.getBasePath();
    }

    public String getImageUrl() {
        return image.getBaseUrl();
    }
    
    public String getMerchantId() {
    	return payment.getMerchantId();
    }
    
    public String getMerchantShaKey() {
    	return payment.getMerchantShaKey();
    }
    
    public String getPaymentTokenRequestUrl() {
    	return payment.getTokenRequestUrl();
    }
    
    public List<String> getCorsOrigins() {
    	return misc.getCorsOrigins();
    }
    
    @Getter
    @Setter
    private static class Misc {
    	private String websiteUrl;
    	private List<String> corsOrigins;
    }
    
    @Getter
    @Setter
    private static class Image {
    	private String basePath;
    	private String baseUrl;
    }
    
    @Getter
    @Setter
    private static class Payment {
    	private String merchantId;
    	private String merchantShaKey;
    	private String tokenRequestUrl;
    }
    
    @Getter
    @Setter
    private static class Firebase {
    	private String apiKey;
    	private String jwkSetUri;
    	private String issuserUri;
    }
    
    @Getter
    @Setter
	public static class SuperUser {
    	private String name;
    	private String email;
    	private String uid;
    }
}
