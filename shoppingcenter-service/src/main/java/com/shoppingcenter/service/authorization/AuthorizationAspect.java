package com.shoppingcenter.service.authorization;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthorizationAspect {

    @Before("execution(* com.shoppingcenter.service.shop.ShopService.findBySlug(..))")
    public void authorizeCreateShop(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Before: " + signature.getName());
    }

}
