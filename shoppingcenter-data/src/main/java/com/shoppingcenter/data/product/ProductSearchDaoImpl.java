package com.shoppingcenter.data.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;
import com.shoppingcenter.search.product.CategoryDocument;
import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.product.ProductSearchRepo;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Repository
public class ProductSearchDaoImpl implements ProductSearchDao {

    @Autowired
    private ProductSearchRepo productSearchRepo;

    @Autowired
    private ShopSearchRepo shopSearchRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public long save(Product product) {
        var shop = product.getShop();
        var shopDocument = shopSearchRepo.findById(shop.getId()).orElseGet(() -> {
            var document = ShopMapper.toDocument(shop);
            return shopSearchRepo.save(document);
        });

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
        productSearchRepo.deleteById(productId);
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
        return productSearchRepo.findSuggestions(q, limit);
    }

    @SuppressWarnings("unused")
	private void visitCategory(Category category, List<CategoryDocument> list) {
        list.add(CategoryMapper.toDocument(category));
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
