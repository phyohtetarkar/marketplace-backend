package com.shoppingcenter.app;

import java.io.IOException;
import java.util.TimeZone;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.core.UploadFile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication(scanBasePackages = { "com.shoppingcenter" })
@EnableAsync
public class ShoppingcenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingcenterApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
		System.out.println("Temp dir: " + System.getProperty("java.io.tmpdir"));
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
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PRIVATE)
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setPropertyCondition(Conditions.isNotNull());
		Converter<MultipartFile, UploadFile> toUploadFile = new Converter<MultipartFile, UploadFile>() {

			@Override
			public UploadFile convert(MappingContext<MultipartFile, UploadFile> context) {
				try {
					MultipartFile source = context.getSource();
					if (source == null || source.isEmpty()) {
						return null;
					}
					UploadFile file = new UploadFile();
					file.setInputStream(source.getInputStream());
					file.setSize(source.getSize());
					file.setOriginalFileName(source.getOriginalFilename());
					return file;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

		};
		modelMapper.addConverter(toUploadFile);
		return modelMapper;
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
								.bearerFormat("JWT"));
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(components)
				.info(info);
	}

}
