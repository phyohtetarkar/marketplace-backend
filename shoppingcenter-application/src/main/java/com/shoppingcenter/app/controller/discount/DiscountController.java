package com.shoppingcenter.app.controller.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.discount.DiscountService;
import com.shoppingcenter.core.discount.model.Discount;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/discounts")
@Tag(name = "Discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public void create(@PathVariable @RequestBody Discount discount) {
        discountService.save(discount);
    }

    @PutMapping
    public void update(@RequestBody Discount discount) {
        discountService.save(discount);
    }

    @GetMapping("{id:\\d+}")
    public Discount findById(@PathVariable long id) {
        return discountService.findById(id);
    }

    @GetMapping
    public PageData<Discount> findAll(
            @RequestParam("shop-id") long shopId,
            @RequestParam(required = false) Integer page) {
        return discountService.findAll(shopId, page);
    }

}
