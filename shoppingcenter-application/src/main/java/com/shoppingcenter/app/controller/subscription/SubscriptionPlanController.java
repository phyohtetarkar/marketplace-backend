package com.shoppingcenter.app.controller.subscription;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPlanDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/subscription-plans")
@Tag(name = "SubscriptionPlan")
public class SubscriptionPlanController {

    @Autowired
    private SubscriptionPlanFacade subscriptionPlanFacade;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SUBSCRIPTION_PLAN_WRITE')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody SubscriptionPlanDTO subscription) {
        subscriptionPlanFacade.save(subscription);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SUBSCRIPTION_PLAN_WRITE')")
    @PutMapping
    public void update(@RequestBody SubscriptionPlanDTO subscription) {
        subscriptionPlanFacade.save(subscription);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SUBSCRIPTION_PLAN_DELETE')")
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        subscriptionPlanFacade.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SUBSCRIPTION_PLAN_READ')")
    @GetMapping("{id:\\d+}")
    public SubscriptionPlanDTO findById(@PathVariable int id) {
        return subscriptionPlanFacade.findById(id);
    }

    @GetMapping
    public List<SubscriptionPlanDTO> getSubscriptionPlans() {
        return subscriptionPlanFacade.findAll();
    }

}
