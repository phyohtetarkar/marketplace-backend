package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-members")
@Tag(name = "ShopMember")
public class ShopMemberController {

    @Autowired
    private ShopMemberFacade shopMemberFacade;

    @GetMapping("{shopId:\\d+}/check-member")
    public boolean isMember(@PathVariable long shopId, Authentication authentication) {
        return shopMemberFacade.isMember(shopId, authentication.getName());
    }
}
