package com.shoppingcenter.app.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Map<String, String> misc;

    private Map<String, String> elasticsearch;

    private Map<String, String> image;
    
    private Map<String, String> payment;

    public String getJwtSecret() {
        return misc.get("jwt-secret");
    }

    public String getDomain() {
        return misc.get("domain");
    }

    public String getElasticUsername() {
        return elasticsearch.get("username");
    }

    public String getElasticPassword() {
        return elasticsearch.get("password");
    }

    public String getImagePath() {
        return image.get("base-path");
    }

    public String getImageUrl() {
        return image.get("base-url");
    }
    
    public String getSmsPohApiKey() {
    	return misc.get("smspoh-apikey");
    }
    
    public String getSmsPohUrl() {
    	return misc.get("smspoh-url");
    }
    
    public String getBrandName() {
    	return misc.get("brand-name");
    }
    
    public String getMerchantId() {
    	return payment.get("merchant-id");
    }
    
    public String getMerchantShaKey() {
    	return payment.get("merchant-sha-key");
    }
    
    public String getPaymentTokenRequestUrl() {
    	return payment.get("token-request-url");
    }

}
