package com.shoppingcenter.app.controller.shop;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopEditDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.product.ProductQueryService;
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

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public void create(@RequestBody ShopEditDTO shop) {
        service.create(modelMapper.map(shop, Shop.class));
    }

    @PutMapping("{id:\\d+}/general")
    public void updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneralDTO general) {
        service.updateGeneralInfo(modelMapper.map(general, ShopGeneral.class));
    }

    @PutMapping("{id:\\d+}/contact")
    public void updateContact(@PathVariable long id, @RequestBody ShopContactDTO contact) {
        service.updateContact(modelMapper.map(contact, ShopContact.class));
    }

    // @GetMapping("{id:\\d+}")
    // public Shop findById(@PathVariable long id) {
    // return shopQueryService.findById(id);
    // }

    @GetMapping("{slug}")
    public ShopDTO findBySlug(@PathVariable String slug) {
        return modelMapper.map(shopQueryService.findBySlug(slug), ShopDTO.class);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug, @RequestParam("exclude") Long excludeId) {
        return shopQueryService.existsBySlug(slug, excludeId);
    }

    // @GetMapping("{id:\\d+}/products")
    // public PageData<ProductDTO> findProductsByShop(
    // @PathVariable long id,
    // @RequestParam(required = false) String q,
    // @RequestParam(required = false) Integer page) {
    // ProductQuery query = ProductQuery.builder()
    // .q(q)
    // .shopId(id)
    // .page(page)
    // .build();
    // return modelMapper.map(productQueryService.findAll(query),
    // ProductDTO.pageType());
    // }

    @GetMapping
    public PageData<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page) {
        ShopQuery query = ShopQuery.builder()
                .q(q)
                .page(page)
                .build();
        return modelMapper.map(shopQueryService.findAll(query), ShopDTO.pageType());
    }

}
