package com.shoppingcenter.data.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;
import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.product.ProductSearchRepo;

@Repository
public class ProductSearchDaoImpl implements ProductSearchDao {

    //@Autowired(required = false)
    private ProductSearchRepo productSearchRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public long save(Product product) {
    	if (productSearchRepo == null) {
    		return 0;
    	}
        var shopDocument = ShopMapper.toDocument(product.getShop());

        var document = productSearchRepo.findById(product.getId()).orElseGet(ProductDocument::new);
        document.setId(product.getId());
        document.setName(product.getName());
        document.setSlug(product.getSlug());
        document.setBrand(product.getBrand());
        document.setPrice(product.getPrice());
        document.setCategory(CategoryMapper.toDocument(product.getCategory()));
        document.setShop(shopDocument);

        var result = productSearchRepo.save(document);

        return result.getId();
    }

    @Override
    public void delete(long productId) {
    	if (productSearchRepo != null) {
    		productSearchRepo.deleteById(productId);
    	}
        
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
    	if (productSearchRepo == null) {
    		return new ArrayList<String>();
    	}
        return productSearchRepo.findSuggestions(q, limit);
    }

}
