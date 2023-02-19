package com.shoppingcenter.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.authorization.AuthenticationContext;
import com.shoppingcenter.service.shop.model.ShopInsights;

@Service
public class ShopDashboardServiceImpl implements ShopDashboardService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private AuthenticationContext authenticationContext;

    @Autowired
    private ShopMemberService shopMemberService;

    @Override
    public ShopInsights getInsights(long shopId) {
        if (!shopRepo.existsById(shopId)) {
            throw new ApplicationException("Shop not found");
        }
        var userId = authenticationContext.getUserId();

        shopMemberService.validateMember(shopId, userId);

        ShopInsights insights = new ShopInsights();
        insights.setTotalProduct(productRepo.countByShop_Id(shopId));

        return insights;
    }

}
