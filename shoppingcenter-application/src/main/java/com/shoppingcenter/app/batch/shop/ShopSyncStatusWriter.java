package com.shoppingcenter.app.batch.shop;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.shoppingcenter.data.shop.ShopRepo;

public class ShopSyncStatusWriter implements Tasklet {

    private ShopRepo repo;

    public ShopSyncStatusWriter(ShopRepo repo) {
        this.repo = repo;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }

}
