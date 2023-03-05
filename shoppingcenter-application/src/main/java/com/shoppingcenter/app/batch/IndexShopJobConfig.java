package com.shoppingcenter.app.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

import com.shoppingcenter.app.batch.shop.IndexShopProcessor;
import com.shoppingcenter.app.batch.shop.IndexShopWriter;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.search.shop.ShopDocument;

@Configuration
public class IndexShopJobConfig {

    private static final int CHUNK_SIZE = 25;

    // @StepScope
    @Bean("shopReader")
    RepositoryItemReader<ProductEntity> reader(ShopRepo repo) {
        // var parameters = stepExecution.getJobExecution().getJobParameters();
        return new RepositoryItemReaderBuilder<ProductEntity>()
                .repository(repo)
                .methodName("findByStatus")
                .arguments(Shop.Status.ACTIVE.name())
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    IndexShopProcessor processor() {
        return new IndexShopProcessor();
    }

    @Bean
    IndexShopWriter writer(ElasticsearchOperations elasticsearchOperations) {
        return new IndexShopWriter(elasticsearchOperations);
    }

    @Bean
    Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("shopReader") RepositoryItemReader<ShopEntity> reader,
            IndexShopProcessor processor,
            IndexShopWriter writer) {
        return new StepBuilder("step1", jobRepository)
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
            Step step1) {
        return new JobBuilder("indexShopJob", jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
