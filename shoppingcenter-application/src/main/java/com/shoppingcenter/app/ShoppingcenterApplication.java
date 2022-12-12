package com.shoppingcenter.app;

import java.util.TimeZone;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@SpringBootApplication(scanBasePackages = {"com.shoppingcenter"})
@EnableAsync
@PropertySource("classpath:environment.properties")
public class ShoppingcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingcenterApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		
	}
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("shoppingcenter-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
		String securitySchemeName = "bearerAuth";
		Info info = new Info()
			      .title("Shopping Center API")
			      .version("1.0")
			      .description("This is a shopping center server created using springdocs - " + 
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
	                        .bearerFormat("JWT")
	                );
	    return new OpenAPI()
	    		.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	    		.components(components)
	    		.info(info);
	}

}
