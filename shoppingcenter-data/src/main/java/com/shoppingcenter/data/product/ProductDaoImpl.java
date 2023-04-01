package com.shoppingcenter.data.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.view.ProductBrandView;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.dao.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private DiscountRepo discountRepo;

    // @Value("${app.image.base-url}")
    // private String imageUrl;

    @Autowired
    private AppProperties properties;

    @Override
    public long save(Product product) {
        var entity = productRepo.findById(product.getId()).orElseGet(ProductEntity::new);

        if (entity.getId() == 0 || !Utils.equalsIgnoreCase(entity.getName(), product.getName())) {
            String prefix = product.getSlug().replaceAll("\\s+", "-").toLowerCase();
            String slug = Utils.generateSlug(prefix, productRepo::existsBySlug);
            entity.setSlug(slug);
        }

        entity.setName(product.getName());
        entity.setBrand(product.getBrand());
        entity.setPrice(product.getPrice());
        entity.setStockLeft(product.getStockLeft());
        entity.setSku(product.getSku());
        entity.setFeatured(product.isFeatured());
        entity.setNewArrival(product.isNewArrival());
        entity.setDescription(product.getDescription());
        entity.setHidden(product.isHidden());
        entity.setThumbnail(product.getThumbnail());
        // entity.setDisabled(product.isDisabled());

        entity.setShop(shopRepo.getReferenceById(product.getShopId()));

        if (product.getDiscountId() != null && discountRepo.existsById(product.getDiscountId())) {
            entity.setDiscount(discountRepo.getReferenceById(product.getDiscountId()));
        } else {
            entity.setDiscount(null);
        }

        var category = categoryRepo.getReferenceById(product.getCategoryId());

        entity.setCategory(category);

        var categoryIds = new HashSet<Integer>();
        visitCategory(category, categoryIds);

        entity.setCategories(categoryIds);

        entity.setWithVariant(product.isWithVariant());

        if (entity.getId() <= 0 && product.getOptions() != null) {
            entity.setOptions(product.getOptions().stream().map(op -> {
                return new ProductOptionEntity(op.getName(), op.getPosition());
            }).collect(Collectors.toSet()));
        }

        var result = productRepo.save(entity);

        return result.getId();
    }

    @Override
    public void updateThumbnail(long id, String thumbnail) {
        productRepo.updateThumbnail(id, thumbnail);
    }

    @Override
    public void delete(long id) {
        productRepo.deleteById(id);
    }

    @Override
    public void removeDiscount(long discountId) {
        productRepo.removeDiscountAll(discountId);
    }

    @Override
    public void toggleDisabled(long id, boolean disabled) {
        productRepo.toggleDisabled(id, disabled);
    }

    @Override
    public void toggleHidden(long id, boolean hidden) {
        productRepo.toggleHidden(id, hidden);
    }

    @Override
    public boolean existsById(long id) {
        return productRepo.existsById(id);
    }

    @Override
    public boolean isAvailable(long id) {
        return productRepo.existsByIdAndHiddenFalseAndDisabledFalse(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return productRepo.existsBySlug(slug);
    }

    @Override
    public boolean existsByCategoryId(int categoryId) {
        return productRepo.existsByCategory_Id(categoryId);
    }

    @Override
    public long countByDiscount(long discountId) {
        return productRepo.countByDiscount_Id(discountId);
    }

    @Override
    public long countByShop(long shopId) {
        return productRepo.countByShop_Id(shopId);
    }

    @Override
    public Product findById(long id) {
        var entity = productRepo.findById(id).orElse(null);
        if (entity != null) {

            var product = ProductMapper.toDomain(entity, properties.getImageUrl());

            if (entity.isWithVariant()) {
                var variants = entity.getVariants().stream().map(e -> {
                    var variant = ProductMapper.toVariant(e);
                    return variant;
                }).collect(Collectors.toList());

                product.setVariants(variants);
            }

            return product;
        }

        return null;
    }

    @Override
    public Product findBySlug(String slug) {
        var entity = productRepo.findBySlug(slug).orElse(null);
        if (entity != null) {
            var product = ProductMapper.toDomain(entity, properties.getImageUrl());

            if (entity.isWithVariant()) {
                var variants = entity.getVariants().stream().map(e -> ProductMapper.toVariant(e))
                        .collect(Collectors.toList());

                product.setVariants(variants);
            }

            return product;

        }

        return null;
    }

    @Override
    public List<Product> findProductHints(String q, int limit) {
        String ql = "%" + q + "%";
        return productRepo.findProductHints(ql, ql, PageRequest.of(0, limit)).stream()
                .map(e -> ProductMapper.toDomainCompat(e, properties.getImageUrl())).toList();
    }

    @Override
    public List<String> findProductBrandsByCategory(String categorySlug) {
        return productRepo.findDistinctBrands(categorySlug).stream().map(ProductBrandView::getBrand).toList();
    }

    @Override
    public List<String> findProductBrandsByCategoryId(int categoryId) {
        return productRepo.findDistinctBrands(categoryId).stream().map(ProductBrandView::getBrand).toList();
    }

    @Override
    public List<Product> getRelatedProducts(long productId, PageQuery pageQuery) {
        var product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            return new ArrayList<>();
        }

        var pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getSize());
        return productRepo
                .findByIdNotAndCategory_IdAndHiddenFalseAndDisabledFalse(productId, product.getCategory().getId(),
                        pageable)
                .map(e -> ProductMapper.toDomainCompat(e, properties.getImageUrl())).toList();
    }

    @Override
    public PageData<Product> getProducts(ProductQuery query) {
        Specification<ProductEntity> spec = null;

        if (query.getShopId() != null && query.getShopId() > 0) {
            Specification<ProductEntity> shopSpec = new BasicSpecification<>(
                    new SearchCriteria("id", Operator.EQUAL, query.getShopId(), "shop"));
            spec = Specification.where(shopSpec);
        }

        if (query.getDiscountId() != null && query.getDiscountId() > 0) {
            Specification<ProductEntity> discountSpec = new BasicSpecification<>(
                    new SearchCriteria("id", Operator.EQUAL, query.getDiscountId(), "discount"));
            spec = spec != null ? spec.and(discountSpec) : Specification.where(discountSpec);
        }

        if (query.getCategoryId() != null && query.getCategoryId() > 0) {
            Specification<ProductEntity> categorySpec = new BasicSpecification<>(
                    SearchCriteria.joinIn("categories", "categories", query.getCategoryId()));
            spec = spec != null ? spec.and(categorySpec) : Specification.where(categorySpec);
        }

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ProductEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, "%" + q + "%"));
            // Specification<ProductEntity> brandSpec = new BasicSpecification<>(
            // new SearchCriteria("brand", Operator.LIKE, "%" + q + "%"));
            // spec = spec != null ? spec.and(nameSpec.or(brandSpec)) :
            // Specification.where(nameSpec.or(brandSpec));
            spec = spec != null ? spec.and(nameSpec) : Specification.where(nameSpec);
        }

        if (query.getBrands() != null && query.getBrands().length > 0) {
            Specification<ProductEntity> brandSpec = new BasicSpecification<>(
                    SearchCriteria.in("brand", (Object[]) query.getBrands()));
            spec = spec != null ? spec.and(brandSpec) : Specification.where(brandSpec);
        }

        if (query.getDiscount() != null && query.getDiscount()) {
            Specification<ProductEntity> discountSpec = new BasicSpecification<>(
                    new SearchCriteria("discount", Operator.NOT_EQ, "NULL"));
            spec = spec != null ? spec.and(discountSpec) : Specification.where(discountSpec);
        }

        if (query.getMaxPrice() != null) {
            Specification<ProductEntity> maxPriceSpec = new BasicSpecification<>(
                    new SearchCriteria("price", Operator.LESS_THAN_EQ, query.getMaxPrice()));
            spec = spec != null ? spec.and(maxPriceSpec) : Specification.where(maxPriceSpec);
        }

        if (query.getHidden() != null) {
            Specification<ProductEntity> hiddenSpec = new BasicSpecification<>(
                    new SearchCriteria("hidden", Operator.EQUAL, query.getHidden()));

            spec = spec != null ? spec.and(hiddenSpec) : Specification.where(hiddenSpec);
        }

        if (query.getDisabled() != null) {
            Specification<ProductEntity> disabledSpec = new BasicSpecification<>(
                    new SearchCriteria("disabled", Operator.EQUAL, query.getDisabled()));

            spec = spec != null ? spec.and(disabledSpec) : Specification.where(disabledSpec);
        }

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = productRepo.findAll(spec, pageable);

        return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e, properties.getImageUrl()));
    }

    private void visitCategory(CategoryEntity category, Set<Integer> list) {
        list.add(category.getId());
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
