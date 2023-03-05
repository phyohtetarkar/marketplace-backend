package com.shoppingcenter.app.event;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.search.product.ProductSearchRepo;

@Component
public class DomainEventListener {

    @Autowired
    @Qualifier("indexProductJob")
    private Job indexProductShopJob;

    @Autowired
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    private ProductSearchRepo productSearchRepo;

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ShopSaveEvent evt) {
            System.out.println("shop save event");
            productSearchRepo.updateShop(evt.getShop().getId());
            // try {
            // var parameters = new JobParametersBuilder()
            // .addLong("shopId", evt.getShop().getId())
            // .toJobParameters();
            // jobLauncher.run(indexProductShopJob, parameters);
            // } catch (JobExecutionAlreadyRunningException e) {
            // e.printStackTrace();
            // } catch (JobRestartException e) {
            // e.printStackTrace();
            // } catch (JobInstanceAlreadyCompleteException e) {
            // e.printStackTrace();
            // } catch (JobParametersInvalidException e) {
            // e.printStackTrace();
            // }
        } else if (event instanceof CategoryUpdateEvent evt) {
            productSearchRepo.updateCategory(CategoryMapper.toDocument(evt.getCategory()));
        }

    }

}
