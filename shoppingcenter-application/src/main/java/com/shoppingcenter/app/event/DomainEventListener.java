package com.shoppingcenter.app.event;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.shoppingcenter.data.product.event.ProductDeleteEvent;
import com.shoppingcenter.data.product.event.ProductSaveEvent;
import com.shoppingcenter.data.product.event.ProductUpdateDiscountEvent;
import com.shoppingcenter.data.shop.event.ShopUpdateEvent;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;

@Component
public class DomainEventListener {

    @Autowired
    @Qualifier("indexProductShopJob")
    private Job indexProductShopJob;

    @Autowired
    @Qualifier("asyncJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    private ProductSearchDao productSearchDao;

    @EventListener
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ProductSaveEvent evt) {
            productSearchDao.save(evt.getProduct());
        } else if (event instanceof ProductDeleteEvent evt) {
            productSearchDao.delete(evt.getProductId());
        } else if (event instanceof ProductUpdateDiscountEvent evt) {
            productSearchDao.setDiscount(evt.getProductIds(), evt.getDiscount());
        } else if (event instanceof ShopUpdateEvent evt) {
            try {
                var parameters = new JobParametersBuilder()
                        .addLong("shopId", evt.getShop().getId())
                        .toJobParameters();
                jobLauncher.run(indexProductShopJob, parameters);
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

}
