package com.shoppingcenter.app.controller.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.product.ProductFacade;
import com.shoppingcenter.app.controller.shop.ShopFacade;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/search")
@Tag(name = "Search")
public class SearchController {

    @Autowired
    private ShopFacade shopFacade;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private SearchFacade searchFacade;

    @Profile("dev")
    @GetMapping("index")
    public void indexAllData() {
        searchFacade.indexAllData();
    }

    @GetMapping("product-hints")
    public List<String> searchProductHints(@RequestParam String q) {
        return productFacade.getHints(q);
    }

    @GetMapping("shop-hints")
    public List<String> searchShopHints(@RequestParam String q) {
        return shopFacade.getHints(q);
    }

}
