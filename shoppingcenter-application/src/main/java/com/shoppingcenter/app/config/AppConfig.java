package com.shoppingcenter.app.config;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.common.LocalFileStorageAdapter;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.common.FileStorageAdapter;

@Configuration
public class AppConfig {

    // @Bean
    // FileStorageService storageService(Environment env) {
    // if (env.acceptsProfiles(Profiles.of("prod"))) {
    // System.out.println("Production environment");
    // }
    // return new LocalStorageService();
    // }

    @Value("${app.image.base-path}")
    private String baseFilePath;

    @Bean
    @Profile("prod")
    FileStorageAdapter prodFileStorageAdapter() {
        return null;
    }

    @Bean
    @Profile("dev")
    FileStorageAdapter devFileStorageAdapter() {
        return new LocalFileStorageAdapter(baseFilePath);
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
    ModelMapper modelMapper() {
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

        return Sanitizers.FORMATTING.and(Sanitizers.BLOCKS)
                .and(Sanitizers.IMAGES)
                .and(Sanitizers.LINKS)
                .and(Sanitizers.TABLES)
                .and(Sanitizers.STYLES);

    }

}
