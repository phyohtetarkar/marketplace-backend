package com.shoppingcenter.app.controller.discount;

import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.shoppingcenter.app.controller.discount.dto.DiscountDTO;
import com.shoppingcenter.app.controller.discount.dto.DiscountEditDTO;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.discount.DiscountService;
import com.shoppingcenter.service.discount.model.Discount;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/discounts")
@Tag(name = "Discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody DiscountEditDTO discount) {
        discountService.save(modelMapper.map(discount, Discount.class));
    }

    @PutMapping
    public void update(@RequestBody DiscountEditDTO discount) {
        discountService.save(modelMapper.map(discount, Discount.class));
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable long id) {
        discountService.delete(id);
    }

    @PostMapping("{id:\\d+}/apply")
    public void applyDiscounts(@PathVariable long id, @RequestBody List<Long> productIds) {
        discountService.applyDiscounts(id, productIds);
    }

    @PostMapping("{id:\\d+}/apply-all")
    public void applyDiscountAll(@PathVariable long id) {
        discountService.applyDiscountAll(id);
    }

    @PostMapping("{id:\\d+}/remove")
    public void removeDiscount(@PathVariable long id, @RequestParam("product-id") long productId) {
        discountService.removeDiscount(id, productId);
    }

    @PostMapping("{id:\\d+}/remove-all")
    public void removeDiscountAll(@PathVariable long id) {
        discountService.removeDiscountAll(id);
    }

    @GetMapping("{id:\\d+}")
    public DiscountDTO findById(@PathVariable long id) {
        return modelMapper.map(discountService.findById(id), DiscountDTO.class);
    }

    @GetMapping
    public PageData<DiscountDTO> findAll(
            @RequestParam("shop-id") long shopId,
            @RequestParam(required = false) Integer page) {
        return modelMapper.map(discountService.findAll(shopId, page), DiscountDTO.pageType());
    }

}
