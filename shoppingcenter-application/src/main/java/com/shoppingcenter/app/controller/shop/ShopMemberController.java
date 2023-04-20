package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.domain.common.AuthenticationContext;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops/{shopId:\\d+}")
@Tag(name = "ShopMember")
public class ShopMemberController {

    @Autowired
    private ShopMemberFacade shopMemberFacade;

    @Autowired
    private AuthenticationContext authentication;

    @GetMapping("check-member")
    public boolean isMember(@PathVariable long shopId) {
        return shopMemberFacade.isMember(shopId, authentication.getUserId());
    }
}
