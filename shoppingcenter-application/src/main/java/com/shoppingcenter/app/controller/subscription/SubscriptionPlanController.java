package com.shoppingcenter.app.controller.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPlanDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/subscription-plans")
@Tag(name = "SubscriptionPlan")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanFacade subscriptionPlanFacade;

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody SubscriptionPlanDTO subscription) {
        subscriptionPlanFacade.save(subscription);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping
    public void update(@RequestBody SubscriptionPlanDTO subscription) {
        subscriptionPlanFacade.save(subscription);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        subscriptionPlanFacade.delete(id);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @GetMapping("{id:\\d+}")
    public SubscriptionPlanDTO findById(@PathVariable int id) {
        return subscriptionPlanFacade.findById(id);
    }

    @GetMapping
    public PageDataDTO<SubscriptionPlanDTO> getSubscriptionPlans(@RequestParam(required = false) Integer page) {
        return subscriptionPlanFacade.findAll(page);
    }

}
