package com.shoppingcenter.service.authorization;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthorizationAspect {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Before("execution(* com.shoppingcenter.service.shop.ShopQueryService.findBySlug(..))")
    public void authorizeUpdateShop(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("User: " + authenticationFacade.getUserId());
        System.out.println("Before: " + signature.getName());
    }

}
