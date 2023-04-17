package com.shoppingcenter.app.config;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.common.LocalFileStorageAdapter;
import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.app.controller.category.dto.CategoryDTO;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.product.dto.ProductImageDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductImage;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.user.User;

@Configuration
public class AppConfig {

	// @Bean
	// FileStorageService storageService(Environment env) {
	// if (env.acceptsProfiles(Profiles.of("prod"))) {
	// System.out.println("Production environment");
	// }
	// return new LocalStorageService();
	// }

	@Bean
	@Profile("prod")
	FileStorageAdapter prodFileStorageAdapter() {
		return null;
	}

	@Bean
	@Profile("dev")
	FileStorageAdapter devFileStorageAdapter(AppProperties properties) {
		return new LocalFileStorageAdapter(properties.getImagePath());
	}

	@Bean
	Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("shoppingcenter-");
		executor.initialize();
		return executor;
	}

	@Bean
	ModelMapper modelMapper(AppProperties properties) {

		var baseUrl = properties.getImageUrl();

		var modelMapper = new ModelMapper();
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

		var bannerMapper = modelMapper.createTypeMap(Banner.class, BannerDTO.class);
		bannerMapper.<String>addMapping(src -> src.getImage(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setImage(baseUrl + "banner/" + img);
			}
		});
		
		var categoryMapper = modelMapper.createTypeMap(Category.class, CategoryDTO.class);
		categoryMapper.<String>addMapping(src -> src.getImage(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setImage(baseUrl + "category/" + img);
			}
		});
		
		var shopMapper = modelMapper.createTypeMap(Shop.class, ShopDTO.class);
		shopMapper.<String>addMapping(src -> src.getLogo(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setLogo(baseUrl + "shop/" + img);
			}
		});
		shopMapper.<String>addMapping(src -> src.getCover(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setCover(baseUrl + "shop/" + img);
			}
		});
		
		var productImageMapper = modelMapper.createTypeMap(ProductImage.class, ProductImageDTO.class);
		productImageMapper.<String>addMapping(src -> src.getName(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setName(baseUrl + "product/" + img);
			}
		});
		
		var productThumbnailMapper = modelMapper.createTypeMap(Product.class, ProductDTO.class);
		productThumbnailMapper.<String>addMapping(src -> src.getThumbnail(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setThumbnail(baseUrl + "product/" + img);
			}
		});
		
		var userMapper = modelMapper.createTypeMap(User.class, UserDTO.class);
		userMapper.<String>addMapping(src -> src.getImage(), (dest, img) -> {
			if (StringUtils.hasText(img)) {
				dest.setImage(baseUrl + "user/" + img);
			}
		});
		

		modelMapper.addConverter(toUploadFile);
		return modelMapper;
	}

	@Bean
	PolicyFactory htmlPolicyFactory() {
		// return new HtmlPolicyBuilder()
		// .allowStandardUrlProtocols()
		// // Allow title="..." on any element.
		// .allowAttributes("title", "style", "class").globally()
		// // Allow href="..." on <a> elements.
		// .allowAttributes("href").onElements("a")
		// // Defeat link spammers.
		// .requireRelNofollowOnLinks()
		// // Allow lang= with an alphabetic value on any element.
		// .allowAttributes("lang").matching(Pattern.compile("[a-zA-Z]{2,20}"))
		// .globally()
		// // The align attribute on <p> elements can have any value below.
		// // .allowAttributes("align")
		// // .matching(true, "center", "left", "right", "justify", "char")
		// // .onElements("p")
		// // These elements are allowed.
		// .allowElements("h1", "h2", "h3", "h5", "h5")
		// .allowElements(
		// "a", "p", "div", "i", "b", "em", "blockquote", "tt", "strong",
		// "br", "ul", "ol", "li")
		// .allowElements("table", "thead", "tbody", "tr", "th", "td")
		// // Custom slashdot tags.
		// // These could be rewritten in the sanitizer using an ElementPolicy.
		// // .allowElements("quote", "ecode")
		// .toFactory();

		var custom = new HtmlPolicyBuilder().allowAttributes("target").onElements("a").toFactory();

		return Sanitizers.FORMATTING.and(Sanitizers.BLOCKS)
				.and(Sanitizers.IMAGES)
				.and(Sanitizers.LINKS)
				.and(Sanitizers.TABLES)
				.and(Sanitizers.STYLES)
				.and(custom);

	}

	@Bean(name = "applicationEventMulticaster")
	ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		var eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return eventMulticaster;
	}

	@Bean(name = "asyncJobLauncher")
	JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		var jobLauncher = new TaskExecutorJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

}
