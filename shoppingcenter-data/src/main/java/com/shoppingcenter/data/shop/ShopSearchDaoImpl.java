package com.shoppingcenter.data.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.search.shop.ShopDocument;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {

    //@Autowired(required = false)
    private ShopSearchRepo shopSearchRepo;

    @Override
    public long save(Shop shop) {
    	if (shopSearchRepo == null) {
    		return 0;
    	}
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
    	if (shopSearchRepo != null) {
    		shopSearchRepo.deleteById(shopId);
    	}
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
    	if (shopSearchRepo == null) {
    		return new ArrayList<String>();
    	}
        return shopSearchRepo.findSuggestions(q, limit);
    }

}
