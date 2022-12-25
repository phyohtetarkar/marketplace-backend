package com.shoppingcenter.core.authorization;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AuthorizationAspect {

    @Before("execution(* com.shoppingcenter.core.shop.ShopServiceImpl.create(..))")
    public void authorizeCreateShop(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Before: " + signature.getName());
    }

}
