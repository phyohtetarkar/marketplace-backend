package com.shoppingcenter.app.controller.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.service.shop.ShopMemberService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shop-members")
@Tag(name = "ShopMember")
public class ShopMemberController {

    @Autowired
    private ShopMemberService service;

    @GetMapping("check")
    public boolean isMember(@RequestParam String slug, Authentication authentication) {
        try {
            service.validateMember(slug, authentication.getName());
            return true;
        } catch (Exception e) {

        }
        return false;
    }
}
