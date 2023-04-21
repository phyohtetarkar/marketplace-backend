package com.shoppingcenter.domain.common;

public interface AppProperties {

    String getApiKey();

    String getElasticUsername();

    String getElasticPassword();

    String getImagePath();

    String getImageUrl();

    String getJwtSecret();

    String getCookieDomain();
}
