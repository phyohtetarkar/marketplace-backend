package com.shoppingcenter.service.authorization;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.service.product.model.Product;
import com.shoppingcenter.service.shop.model.ShopContact;
import com.shoppingcenter.service.shop.model.ShopGeneral;

@Component
@Aspect
public class AuthorizationAspect {

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Before("execution(* com.shoppingcenter.service.shop.ShopService.updateGeneralInfo(..))")
    public void authorizeUpdateShopGeneral(JoinPoint joinPoint) {
        String userId = authenticationContext.getUserId();
        ShopGeneral general = (ShopGeneral) joinPoint.getArgs()[0];
        validateShopManagePermission(general.getShopId(), userId);
    }

    @Before("execution(* com.shoppingcenter.service.shop.ShopService.updateContact(..))")
    public void authorizeUpdateShopContact(JoinPoint joinPoint) {
        String userId = authenticationContext.getUserId();
        ShopContact contact = (ShopContact) joinPoint.getArgs()[0];
        validateShopManagePermission(contact.getShopId(), userId);
    }

    @Before("execution(* com.shoppingcenter.service.shop.ShopService.uploadLogo(..))")
    public void authorizeUpdateShopLogo(JoinPoint joinPoint) {
        String userId = authenticationContext.getUserId();
        long shopId = (Long) joinPoint.getArgs()[0];
        validateShopManagePermission(shopId, userId);
    }

    @Before("execution(* com.shoppingcenter.service.shop.ShopService.uploadCover(..))")
    public void authorizeUpdateShopCover(JoinPoint joinPoint) {
        String userId = authenticationContext.getUserId();
        long shopId = (Long) joinPoint.getArgs()[0];
        validateShopManagePermission(shopId, userId);
    }

    @Before("execution(* com.shoppingcenter.service.product.ProductService.save(..))")
    public void authorizeManageProduct(JoinPoint joinPoint) {
        // MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String userId = authenticationContext.getUserId();
        // System.out.println("User: " + userId);
        Product product = (Product) joinPoint.getArgs()[0];

        validateShopManagePermission(product.getShopId(), userId);
    }

    private void validateShopManagePermission(long shopId, String userId) {
        if (!shopMemberRepo.existsByShop_IdAndUser_Id(shopId, userId)) {
            throw new AccessDeniedException("Permission denied");
        }
    }

}
