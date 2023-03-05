package com.shoppingcenter.app.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class IndexElasticsearchJobCompletionListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(IndexElasticsearchJobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Index es job started at: {}", jobExecution.getStartTime().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Index es job finished at: {}", jobExecution.getEndTime().toString());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Index es job success");
        } else {
            logger.info("Index es job failed");
        }
    }
}
