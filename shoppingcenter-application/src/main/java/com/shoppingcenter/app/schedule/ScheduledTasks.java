package com.shoppingcenter.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    @Qualifier("indexProductJob")
    private Job indexProductJob;

    @Autowired
    @Qualifier("indexShopJob")
    private Job indexShopJob;

    @Autowired
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    /**
     * Run 00:00 AM every day
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+6:30")
    public void removePendingShops() {
        log.info("Remove pending shops now");
    }

    /**
     * Run 00:00 AM every day
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+6:30")
    public void checkSubscriptions() {
        log.info("Check shop subscriptions now");
    }

    /**
     * Run 3:00 AM and 9:00 PM every day
     */
    // @Scheduled(cron = "0 0 3,21 * * *", zone = "GMT+6:30")
    // public void startShopsIndexing() {
    // try {
    // var parameters = new JobParametersBuilder()
    // .addLong("time", System.currentTimeMillis())
    // .toJobParameters();
    // jobLauncher.run(indexShopJob, parameters);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * Run 3:00 AM and 9:00 PM every day
     */
    // @Scheduled(cron = "0 0 3,21 * * *", zone = "GMT+6:30")
    // public void startProductsIndexing() {
    // try {
    // var parameters = new JobParametersBuilder()
    // .addLong("time", System.currentTimeMillis())
    // .toJobParameters();
    // jobLauncher.run(indexProductJob, parameters);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
}
