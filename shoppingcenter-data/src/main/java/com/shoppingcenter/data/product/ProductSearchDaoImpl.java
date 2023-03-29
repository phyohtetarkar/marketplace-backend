package com.shoppingcenter.data.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
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
        document.setCreatedAt(product.getCreatedAt());

        document.setCategory(CategoryMapper.toDocument(product.getCategory()));
        document.setShop(shopDocument);

        var categories = new ArrayList<CategoryDocument>();
        visitCategory(product.getCategory(), categories);

        document.setCategories(categories);

        // var images = product.getImages().stream().map(value -> {
        // var image = new ProductImageDocument();
        // image.setId(value.getId());
        // image.setName(value.getName());
        // image.setThumbnail(value.isThumbnail());
        // image.setSize(value.getSize());
        // return image;
        // }).collect(Collectors.toList());

        // document.setImages(images);

        if (product.isWithVariant()) {
            // var options = product.getOptions().stream().map(value -> {
            // var option = new ProductOptionDocument();
            // option.setEntityId(value.getId());
            // option.setName(value.getName());
            // option.setPosition(value.getPosition());
            // return option;
            // }).collect(Collectors.toList());

            // var variants = product.getVariants().stream().map(value -> {
            // var variant = new ProductVariantDocument();
            // variant.setEntityId(value.getId());
            // variant.setTitle(value.getTitle());
            // return variant;
            // }).collect(Collectors.toList());
        }

        var result = productSearchRepo.save(document);

        return result.getId();
    }

    @Override
    public void delete(long productId) {
        productSearchRepo.deleteById(productId);
    }

    @Override
    public List<String> getProductBrands(String categorySlug) {
        var criteria = new Criteria("categories.slug").is(categorySlug);
        var query = new CriteriaQuery(criteria);
        query.addSourceFilter(new FetchSourceFilterBuilder().withIncludes("brand").build());
        return productSearchRepo.findAll(query).stream().map(ProductDocument::getBrand).toList();
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
        return productSearchRepo.findSuggestions(q, limit);
    }

    private void visitCategory(Category category, List<CategoryDocument> list) {
        list.add(CategoryMapper.toDocument(category));
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
