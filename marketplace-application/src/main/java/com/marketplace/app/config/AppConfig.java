package com.marketplace.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

import com.marketplace.app.common.AppProperties;
import com.marketplace.app.common.LocalFileStorageAdapter;
import com.marketplace.domain.common.FileStorageAdapter;

@Configuration
public class AppConfig {

	@Bean
	@Profile("prod")
	FileStorageAdapter prodFileStorageAdapter(AppProperties properties) {
		return new LocalFileStorageAdapter(properties.getImagePath());
	}

	@Bean
	@Profile({"dev", "staging"})
	FileStorageAdapter devFileStorageAdapter(AppProperties properties) {
		return new LocalFileStorageAdapter(properties.getImagePath());
	}
	
	@Bean
	RestClient restClient() {
		return RestClient.create();
	}

//	@Bean
//	ModelMapper modelMapper() {
//		var modelMapper = new ModelMapper();
//		modelMapper.getConfiguration()
//			.setFieldMatchingEnabled(true)
//			.setFieldAccessLevel(AccessLevel.PRIVATE)
//			.setMatchingStrategy(MatchingStrategies.STRICT)
//			.setPropertyCondition(Conditions.isNotNull());
//		
//		Converter<MultipartFile, UploadFile> toUploadFile = new Converter<MultipartFile, UploadFile>() {
//
//			@Override
//			public UploadFile convert(MappingContext<MultipartFile, UploadFile> context) {
//				MultipartFile source = context.getSource();
//				if (source == null || source.isEmpty()) {
//					return null;
//				}
//				UploadFile file = new UploadFile();
//				file.setResource(source.getResource());
//				file.setSize(source.getSize());
//				file.setOriginalFileName(source.getOriginalFilename());
//				return file;
//			}
//
//		};
//
//		modelMapper.addConverter(toUploadFile);
//		return modelMapper;
//	}

//	@Bean(name = "applicationEventMulticaster")
//	ApplicationEventMulticaster simpleApplicationEventMulticaster() {
//		var eventMulticaster = new SimpleApplicationEventMulticaster();
//		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		return eventMulticaster;
//	}

//	@Bean(name = "asyncJobLauncher")
//	JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
//		var jobLauncher = new TaskExecutorJobLauncher();
//		jobLauncher.setJobRepository(jobRepository);
//		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		jobLauncher.afterPropertiesSet();
//		return jobLauncher;
//	}

}
