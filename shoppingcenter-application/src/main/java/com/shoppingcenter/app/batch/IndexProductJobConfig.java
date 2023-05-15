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

import com.shoppingcenter.app.batch.product.IndexProductProcessor;
import com.shoppingcenter.app.batch.product.IndexProductWriter;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.search.product.ProductDocument;

@Configuration
public class IndexProductJobConfig {

    private static final int CHUNK_SIZE = 50;

    // @StepScope
    @Bean("productReader")
    RepositoryItemReader<ProductEntity> reader(ProductRepo repo) {
        // var parameters = stepExecution.getJobExecution().getJobParameters();
        return new RepositoryItemReaderBuilder<ProductEntity>()
                .repository(repo)
                .methodName("findAll")
                .pageSize(CHUNK_SIZE)
                .sorts(Map.of("modifiedAt", Sort.Direction.ASC))
                .name("productReader")
                .build();
    }

    @Bean("productProcessor")
    IndexProductProcessor processor() {
        return new IndexProductProcessor();
    }

    @Bean("productWriter")
    IndexProductWriter writer(Optional<ElasticsearchOperations> elasticsearchOperations) {
        return new IndexProductWriter(elasticsearchOperations.orElse(null));
    }

    @Bean("productStep1")
    Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("productReader") RepositoryItemReader<ProductEntity> reader,
            @Qualifier("productProcessor") IndexProductProcessor processor,
            @Qualifier("productWriter") IndexProductWriter writer) {
        return new StepBuilder("productStep1", jobRepository)
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
            @Qualifier("productStep1") Step step1) {
        return new JobBuilder("indexProductJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
