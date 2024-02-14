package com.marketplace.api.vendor.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops/{shopId:\\d+}/discounts")
@PreAuthorize("@authz.isShopMember(#shopId)")
@Tag(name = "Vendor")
public class DiscountController {

	@Autowired
	private DiscountControllerFacade discountFacade;
	
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@PathVariable long shopId, @RequestBody DiscountEditDTO body) {
        discountFacade.save(shopId, body);
    }

    @PutMapping
    public void update(@PathVariable long shopId, @RequestBody DiscountEditDTO body) {
        discountFacade.save(shopId, body);
    }
    
    @PostMapping("{id:\\d+}/apply")
    public void applyDiscounts(
    		@PathVariable long shopId, 
    		@PathVariable long id, 
    		@RequestBody List<Long> productIds) {
    	discountFacade.applyDiscounts(shopId, id, productIds);
    }
    
    @GetMapping
    public List<DiscountDTO> findAll(@PathVariable long shopId) {
    	return discountFacade.findByShop(shopId);
        
    }
}
