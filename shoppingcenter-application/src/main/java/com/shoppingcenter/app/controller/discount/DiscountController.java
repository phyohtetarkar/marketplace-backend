package com.shoppingcenter.app.controller.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountEditDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/discounts")
@Tag(name = "Discount")
public class DiscountController {

    @Autowired
    private DiscountFacade discountFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody DiscountEditDTO discount) {
        discountFacade.save(discount);
    }

    @PutMapping
    public void update(@RequestBody DiscountEditDTO discount) {
        discountFacade.save(discount);
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable long id) {
        discountFacade.delete(id);
    }

    @PostMapping("{id:\\d+}/apply")
    public void applyDiscounts(@PathVariable long id, @RequestBody List<Long> productIds) {
        discountFacade.applyDiscounts(id, productIds);
    }

    @PostMapping("{id:\\d+}/apply-all")
    public void applyDiscountAll(@PathVariable long id) {
        discountFacade.applyDiscounts(id, null);
    }

    @PostMapping("{id:\\d+}/remove")
    public void removeDiscount(@PathVariable long id, @RequestParam("product-id") long productId) {
        discountFacade.removeDiscount(id, productId);
    }

    @PostMapping("{id:\\d+}/remove-all")
    public void removeDiscountAll(@PathVariable long id) {
        discountFacade.removeDiscount(id, null);
    }

    @GetMapping("{id:\\d+}")
    public DiscountDTO findById(@PathVariable long id) {
        return discountFacade.findById(id);
    }

    @GetMapping
    public PageDataDTO<DiscountDTO> findAll(
            @RequestParam("shop-id") long shopId,
            @RequestParam(required = false) Integer page) {
        return discountFacade.findByShop(shopId, page);
    }

}
