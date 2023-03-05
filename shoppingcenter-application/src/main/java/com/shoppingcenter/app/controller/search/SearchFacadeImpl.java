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
public class SearchFacadeImpl implements SearchFacade {

    @Autowired
    @Qualifier("indexProductJob")
    private Job indexProductJob;

    @Autowired
    @Qualifier("indexShopJob")
    private Job indexShopJob;

    @Autowired
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    @Override
    public void indexAllData() {
        try {
            var parameters = new JobParametersBuilder()
                    .toJobParameters();
            jobLauncher.run(indexProductJob, parameters);
            jobLauncher.run(indexShopJob, parameters);
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
