package com.shoppingcenter.app.batch.product;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.shoppingcenter.data.product.ProductRepo;

public class ProductSyncStatusWriter implements Tasklet {

    private ProductRepo repo;

    public ProductSyncStatusWriter(ProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }

}
