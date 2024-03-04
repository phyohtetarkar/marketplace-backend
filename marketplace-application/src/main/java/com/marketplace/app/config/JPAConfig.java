package com.marketplace.app.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.domain.user.User;

@Configuration
@EntityScan(basePackages = { "com.marketplace.data" })
@EnableJpaRepositories(basePackages = "com.marketplace.data")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(AuthenticationUtil.getAuthenticatedUser())
                .map(User::getUid);
    }
}
