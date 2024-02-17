package com.marketplace.app.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.marketplace.domain.subscription.usecase.RemoveUnprocessedSubscriptionsUseCase;

@Component
public class ScheduleHandler {

    private static final Logger log = LoggerFactory.getLogger(ScheduleHandler.class);
    
    @Autowired
    private RemoveUnprocessedSubscriptionsUseCase removeUnprocessedSubscriptionsUseCase;

    /**
     * Run 00:00 AM every day
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+6:30")
    public void checkOverdueSubscriptions() {
        //TODO : implement later
    }
    
    /**
     * Run 00:00 AM every day
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+6:30")
    public void removeUnprocessedSubscriptions() {
    	log.info("Remove unprocessed subscriptions");
    	removeUnprocessedSubscriptionsUseCase.apply();
    }

    /**
     * Run 3:00 AM and 9:00 PM every day
     */

    /**
     * Run 3:00 AM and 9:00 PM every day
     */
}
