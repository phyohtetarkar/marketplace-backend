package com.shoppingcenter.app.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.PlatformTransactionManager;

import com.shoppingcenter.app.batch.product.IndexProductJobCompletionListener;
import com.shoppingcenter.app.batch.product.IndexProductShopProcessor;
import com.shoppingcenter.app.batch.product.IndexProductShopWriter;
import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.product.ProductSearchRepo;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Configuration
public class IndexProductByShopJobConfig {

    @StepScope
    @Bean
    RepositoryItemReader<ProductDocument> reader(ProductSearchRepo repo, StepExecution stepExecution) {
        var parameters = stepExecution.getJobExecution().getJobParameters();
        return new RepositoryItemReaderBuilder<ProductDocument>()
                .repository(repo)
                .methodName("findByShop_EntityId")
                .arguments(parameters.getLong("shopId"))
                .pageSize(25)
                .build();
    }

    @StepScope
    @Bean
    IndexProductShopProcessor processor(ShopSearchRepo repo, StepExecution stepExecution) {
        var parameters = stepExecution.getJobExecution().getJobParameters();
        var shop = repo.findByEntityId(parameters.getLong("shopId")).orElse(null);
        return new IndexProductShopProcessor(shop);
    }

    @Bean
    IndexProductShopWriter writer(ElasticsearchOperations elasticsearchOperations) {
        return new IndexProductShopWriter(elasticsearchOperations);
    }

    @Bean
    Step step1(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            RepositoryItemReader<ProductDocument> reader,
            IndexProductShopProcessor processor,
            IndexProductShopWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<ProductDocument, ProductDocument>chunk(125, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "indexProductShopJob")
    Job indexProductShopJob(
            JobRepository jobRepository,
            IndexProductJobCompletionListener listener,
            Step step1) {
        return new JobBuilder("indexProductShopJob", jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
