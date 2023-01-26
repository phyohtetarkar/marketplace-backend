package com.shoppingcenter.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+6:30")
    public void removePendingShops() {
        log.info("Remove pending shops now");
    }
}
