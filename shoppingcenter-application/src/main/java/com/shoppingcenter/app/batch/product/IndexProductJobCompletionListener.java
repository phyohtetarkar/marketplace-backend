package com.shoppingcenter.app.batch.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class IndexProductJobCompletionListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(IndexProductJobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Index product job started at: {}", jobExecution.getStartTime().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Index product job finished at: {}", jobExecution.getEndTime().toString());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("Index product job success");
        }
    }
}
