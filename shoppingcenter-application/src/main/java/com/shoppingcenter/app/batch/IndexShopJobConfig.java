package com.shoppingcenter.app.batch;

import java.util.Map;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

import com.shoppingcenter.app.batch.shop.IndexShopProcessor;
import com.shoppingcenter.app.batch.shop.IndexShopWriter;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.search.shop.ShopDocument;

@Configuration
public class IndexShopJobConfig {

    private static final int CHUNK_SIZE = 50;

    // @StepScope
    @Bean("shopReader")
    RepositoryItemReader<ShopEntity> reader(ShopRepo repo) {
        // var parameters = stepExecution.getJobExecution().getJobParameters();
        return new RepositoryItemReaderBuilder<ShopEntity>()
                .repository(repo)
                .methodName("findAll")
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("modifiedAt", Sort.Direction.DESC))
                .name("shopReader")
                .build();
    }

    @Bean("shopProcessor")
    IndexShopProcessor processor() {
        return new IndexShopProcessor();
    }

    @Bean("shopWriter")
    IndexShopWriter writer(Optional<ElasticsearchOperations> elasticsearchOperations) {
        return new IndexShopWriter(elasticsearchOperations.orElse(null));
    }

    @Bean("shopStep1")
    Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("shopReader") RepositoryItemReader<ShopEntity> reader,
            @Qualifier("shopProcessor") IndexShopProcessor processor,
            @Qualifier("shopWriter") IndexShopWriter writer) {
        return new StepBuilder("shopStep1", jobRepository)
                .<ShopEntity, ShopDocument>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retryLimit(3)
                .retry(BulkFailureException.class)
                .build();
    }

    @Bean(name = "indexShopJob")
    Job indexShopJob(
            JobRepository jobRepository,
            IndexElasticsearchJobCompletionListener listener,
            @Qualifier("shopStep1") Step step1) {
        return new JobBuilder("indexShopJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
