package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.search.shop.ShopDocument;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {

    @Autowired
    private ShopSearchRepo shopSearchRepo;

    @Override
    public long save(Shop shop) {
        var document = shopSearchRepo.findById(shop.getId()).orElseGet(ShopDocument::new);
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());

        var result = shopSearchRepo.save(document);
        return result.getId();
    }

    @Override
    public void delete(long shopId) {
        shopSearchRepo.deleteById(shopId);
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
        return shopSearchRepo.findSuggestions(q, limit);
    }

}
