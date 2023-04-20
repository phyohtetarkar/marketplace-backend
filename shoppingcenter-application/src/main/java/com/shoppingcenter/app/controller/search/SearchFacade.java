package com.shoppingcenter.app.controller.search;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.shoppingcenter.app.annotation.Facade;

@Facade
public class SearchFacade {

    @Autowired
    @Qualifier("indexProductJob")
    private Job indexProductJob;

    @Autowired
    @Qualifier("indexShopJob")
    private Job indexShopJob;

    @Autowired
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    public void indexAllData() {
        try {
            jobLauncher.run(indexProductJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
            jobLauncher.run(indexShopJob, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
