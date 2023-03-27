package com.shoppingcenter.data.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
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
        document.setStatus(product.getStatus().name());
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

    @Override
    public List<Product> getHints(String q, int limit) {
        var criteria = new Criteria("status").is(Product.Status.PUBLISHED.name())
                .and("name").matchesAll(q);

        var pageable = PageRequest.of(0, limit);

        var pageResult = productSearchRepo.findAll(new CriteriaQuery(criteria, pageable), pageable);

        return pageResult.get().map(v -> ProductMapper.toDomainCompat(v.getContent(), imageUrl)).toList();
    }

    @Override
    public List<Product> getRelatedProducts(long productId, int limit) {
        var document = productSearchRepo.findById(productId).orElse(null);
        if (document == null) {
            return new ArrayList<>();
        }

        var fields = new String[] { "name", "categories.name" };

        var pageable = PageRequest.of(0, limit);

        return productSearchRepo.searchSimilar(document, fields, pageable)
                .map(doc -> ProductMapper.toDomainCompat(doc, imageUrl))
                .toList();
    }

    @Override
    public PageData<Product> getProducts(ProductQuery query) {
        var criteria = new Criteria();

        if (query.getShopId() != null && query.getShopId() > 0) {
            criteria = criteria.and("shop.entityId").is(query.getShopId());
        }

        if (query.getDiscountId() != null && query.getDiscountId() > 0) {
            criteria = criteria.and("discount.entityId").is(query.getDiscountId());
        }

        if (StringUtils.hasText(query.getCategorySlug())) {
            criteria = criteria.and("categories.slug").is(query.getCategorySlug());
        }

        if (query.getStatus() != null) {
            criteria = criteria.and("status").is(query.getStatus());
        } else if (query.getStatusNot() != null) {
            criteria = criteria.and("status").is(query.getStatusNot()).not();
        }

        if (query.getBrands() != null && query.getBrands().length > 0) {
            criteria = criteria.and("brand").in(Arrays.asList(query.getBrands()));
        }

        if (query.getDiscount() != null && query.getDiscount()) {
            criteria = criteria.and("discount").exists();
        }

        if (query.getMaxPrice() != null) {
            criteria = criteria.and("price").lessThan(query.getMaxPrice());
        }

        if (StringUtils.hasText(query.getQ())) {
            var q = query.getQ().toLowerCase();
            criteria = criteria.and("name").matchesAll(q);
        }

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = productSearchRepo.findAll(new CriteriaQuery(criteria, pageable), pageable);

        return PageDataMapper.map(pageResult, doc -> ProductMapper.toDomainCompat(doc.getContent(), imageUrl));
    }

    private void visitCategory(Category category, List<CategoryDocument> list) {
        list.add(CategoryMapper.toDocument(category));
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
