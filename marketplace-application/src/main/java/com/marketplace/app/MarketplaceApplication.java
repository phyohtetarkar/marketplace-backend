package com.marketplace.app;

import java.util.TimeZone;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.marketplace.app.common.AppProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "com.marketplace" })
@EnableAsync
@EnableScheduling
@EnableRetry
@ImportRuntimeHints({ NativeRuntimeHints.class })
@EnableConfigurationProperties(AppProperties.class)
public class MarketplaceApplication {

	public static void main(String[] args) {
//		SpringApplication.run(MarketplaceApplication.class, args);
		new SpringApplicationBuilder(MarketplaceApplication.class)
			.beanNameGenerator(new CustomGenerator())
			.run(args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		// System.out.println("Temp dir: " + System.getProperty("java.io.tmpdir"));
	}
	
	public static class CustomGenerator extends AnnotationBeanNameGenerator {

        @Override
        public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        	var beanClassName = definition.getBeanClassName();
        	if (beanClassName.startsWith("com.marketplace.api.consumer")) {
        		return "consumer-" + super.generateBeanName(definition, registry);
        	}
        	
        	if (beanClassName.startsWith("com.marketplace.api.vendor")) {
        		return "vendor-" + super.generateBeanName(definition, registry);
        	}
        	
        	if (beanClassName.startsWith("com.marketplace.api.admin")) {
        		return "admin-" + super.generateBeanName(definition, registry);
        	}
            return super.generateBeanName(definition, registry);
        }
    }

	@Bean
	@Profile({"dev", "staging"})
	OpenAPI customOpenAPI() {
		String securitySchemeName = "bearerAuth";
		Info info = new Info()
				.title("Marketplace API")
				.version("1.0")
				.description("This is a marketplace server created using springdocs - " +
						"a library for OpenAPI 3 with spring boot.")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0")
						.url("http://springdoc.org"));

		Components components = new Components()
				.addSecuritySchemes(securitySchemeName,
						new SecurityScheme()
								.name(securitySchemeName)
								.type(SecurityScheme.Type.HTTP)
								.scheme("Bearer")
								.bearerFormat("JWT"));
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(components)
				.info(info);
	}

}
