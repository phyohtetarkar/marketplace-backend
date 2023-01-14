package com.shoppingcenter.app.controller.shop;

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
import com.shoppingcenter.core.product.ProductQuery;
import com.shoppingcenter.core.product.ProductQueryService;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.shop.ShopQuery;
import com.shoppingcenter.core.shop.ShopQueryService;
import com.shoppingcenter.core.shop.ShopService;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.core.shop.model.ShopContact;
import com.shoppingcenter.core.shop.model.ShopGeneral;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops")
@Tag(name = "Shop")
public class ShopController {

    @Autowired
    private ShopService service;

    @Autowired
    private ShopQueryService shopQueryService;

    @Autowired
    private ProductQueryService productQueryService;

    @PostMapping
    public void create(@RequestBody Shop shop) {
        service.create(shop);
    }

    @PutMapping("{id:\\d+}/general")
    public void updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneral general) {
        service.updateGeneralInfo(general);
    }

    @PutMapping("{id:\\d+}/contact")
    public void updateContact(@PathVariable long id, @RequestBody ShopContact contact) {
        service.updateContact(contact);
    }

    // @GetMapping("{id:\\d+}")
    // public Shop findById(@PathVariable long id) {
    // return shopQueryService.findById(id);
    // }

    @GetMapping("{slug}")
    public Shop findBySlug(@PathVariable String slug) {
        return shopQueryService.findBySlug(slug);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug) {
        return shopQueryService.existsBySlug(slug);
    }

    @GetMapping("{id:\\d+}/products")
    public PageData<Product> findProductsByShop(
            @PathVariable long id,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page) {
        ProductQuery query = ProductQuery.builder()
                .q(q)
                .shopId(id)
                .page(page)
                .build();
        return productQueryService.findAll(query);
    }

    @GetMapping
    public PageData<Shop> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page) {
        ShopQuery query = ShopQuery.builder()
                .q(q)
                .page(page)
                .build();
        return shopQueryService.findAll(query);
    }

}
