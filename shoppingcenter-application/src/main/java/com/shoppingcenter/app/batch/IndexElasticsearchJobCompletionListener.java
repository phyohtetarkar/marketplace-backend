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
        var job = jobExecution.getJobInstance().getJobName();
        logger.info("{} started at: {}", job, jobExecution.getStartTime().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        var job = jobExecution.getJobInstance().getJobName();
        logger.info("{} finished at: {}", job, jobExecution.getEndTime().toString());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("{} success", job);
        } else {
            logger.info("{} failed", job);
        }
    }
}
