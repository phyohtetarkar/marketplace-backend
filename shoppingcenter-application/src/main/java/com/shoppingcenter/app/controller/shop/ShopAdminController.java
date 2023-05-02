package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.domain.shop.ShopQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/shops")
@Tag(name = "Shop")
public class ShopAdminController {

    @Autowired
    private ShopFacade shopFacade;

//    @PutMapping("${id:\\d+}/activate")
//    public void makeActive(@PathVariable long id) {
//        shopFacade.updateStatus(id, Shop.Status.ACTIVE);
//    }

    @PutMapping("${id:\\d+}/enable")
    public void enableShop(@PathVariable long id) {
    	shopFacade.updateDisabled(id, true);
    }
    
    @PutMapping("${id:\\d+}/disable")
    public void disableShop(@PathVariable long id) {
    	shopFacade.updateDisabled(id, false);
    }

    @GetMapping
    public PageDataDTO<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean expired,
            @RequestParam(required = false) Boolean disabled,
            @RequestParam(required = false) Integer page) {
        var query = ShopQuery.builder()
                .q(q)
                .expired(expired)
                .disabled(disabled)
                .page(page)
                .build();
        return shopFacade.findAll(query);
    }
}
