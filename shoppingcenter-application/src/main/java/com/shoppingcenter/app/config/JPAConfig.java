package com.shoppingcenter.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shoppingcenter.app.AuditorAwareImpl;

@Configuration
@EntityScan(basePackages = { "com.shoppingcenter.data" })
@EnableJpaRepositories(basePackages = "com.shoppingcenter.data")
@EnableJpaAuditing
public class JPAConfig {

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
