package com.marketplace.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.marketplace.app.AuditorAwareImpl;

@Configuration
@EntityScan(basePackages = { "com.marketplace.data" })
@EnableJpaRepositories(basePackages = "com.marketplace.data")
@EnableJpaAuditing
public class JPAConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
