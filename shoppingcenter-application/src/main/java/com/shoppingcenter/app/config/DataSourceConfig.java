package com.shoppingcenter.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

//@Configuration
public class DataSourceConfig {
	
	@Value("${datasource.url}")
	private String url;
	@Value("${datasource.username}")
	private String username;
	@Value("${datasource.password}")
	private String password;

	@Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
        		.url(url)
        		.username(username)
        		.password(password)
        		.build();
    }
	
}
