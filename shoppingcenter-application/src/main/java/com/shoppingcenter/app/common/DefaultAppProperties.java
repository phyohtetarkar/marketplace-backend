package com.shoppingcenter.app.common;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.shoppingcenter.domain.common.AppProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class DefaultAppProperties implements AppProperties {

    private Map<String, String> security;

    private Map<String, String> elasticsearch;

    private Map<String, String> image;

    @Override
    public String getApiKey() {
        return security.get("api-key");
    }

    @Override
    public String getJwtSecret() {
        return security.get("jwt-secret");
    }

    @Override
    public String getDomain() {
        return security.get("domain");
    }

    @Override
    public String getElasticUsername() {
        return elasticsearch.get("username");
    }

    @Override
    public String getElasticPassword() {
        return elasticsearch.get("password");
    }

    @Override
    public String getImagePath() {
        return image.get("base-path");
    }

    @Override
    public String getImageUrl() {
        return image.get("base-url");
    }

}
