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

import com.shoppingcenter.app.batch.product.IndexProductProcessor;
import com.shoppingcenter.app.batch.product.IndexProductWriter;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.search.product.ProductDocument;

@Configuration
public class IndexProductJobConfig {

    private static final int CHUNK_SIZE = 25;

    // @StepScope
    @Bean("productReader")
    RepositoryItemReader<ProductEntity> reader(ProductRepo repo) {
        // var parameters = stepExecution.getJobExecution().getJobParameters();
        return new RepositoryItemReaderBuilder<ProductEntity>()
                .repository(repo)
                .methodName("findByStatus")
                .arguments(Product.Status.PUBLISHED.name())
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    IndexProductProcessor processor() {
        return new IndexProductProcessor();
    }

    @Bean
    IndexProductWriter writer(ElasticsearchOperations elasticsearchOperations) {
        return new IndexProductWriter(elasticsearchOperations);
    }

    @Bean
    Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("productReader") RepositoryItemReader<ProductEntity> reader,
            IndexProductProcessor processor,
            IndexProductWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<ProductEntity, ProductDocument>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retryLimit(3)
                .retry(BulkFailureException.class)
                .build();
    }

    @Bean(name = "indexProductJob")
    Job indexProductJob(
            JobRepository jobRepository,
            IndexElasticsearchJobCompletionListener listener,
            Step step1) {
        return new JobBuilder("indexProductJob", jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
