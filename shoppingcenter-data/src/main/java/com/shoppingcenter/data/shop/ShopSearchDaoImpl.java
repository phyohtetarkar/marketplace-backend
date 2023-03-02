package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {

    @Autowired
    private ShopSearchRepo shopSearchRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public String save(Shop shop) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(long shopId) {
        shopSearchRepo.deleteByEntityId(shopId);
    }

    @Override
    public PageData<Shop> getShops(ShopQuery query) {
        // TODO Auto-generated method stub
        return null;
    }

}
