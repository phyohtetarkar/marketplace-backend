package com.shoppingcenter.data.product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.discount.DiscountRepo;
import com.shoppingcenter.data.product.event.ProductDeleteEvent;
import com.shoppingcenter.data.product.event.ProductSaveEvent;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.Product.Status;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductSearchDao;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ProductSearchDao productSearchDao;

    @Value("${app.image.base-url}")
    private String imageUrl;

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
        entity.setStatus(product.getStatus().name());
        entity.setDescription(product.getDescription());
        entity.setCategory(categoryRepo.getReferenceById(product.getCategoryId()));
        entity.setShop(shopRepo.getReferenceById(product.getShopId()));

        if (product.getDiscountId() != null && discountRepo.existsById(product.getDiscountId())) {
            entity.setDiscount(discountRepo.getReferenceById(product.getDiscountId()));
        } else {
            entity.setDiscount(null);
        }

        entity.setWithVariant(product.isWithVariant());

        var result = productRepo.save(entity);

        eventPublisher.publishEvent(new ProductSaveEvent(this, ProductMapper.toDomain(result, imageUrl)));

        return result.getId();
    }

    @Override
    public void delete(long id) {
        productRepo.deleteById(id);
        eventPublisher.publishEvent(new ProductDeleteEvent(this, id));
    }

    @Override
    public void removeDiscount(long discountId) {
        productRepo.removeDiscountAll(discountId);
    }

    @Override
    public boolean existsById(long id) {
        return productRepo.existsById(id);
    }

    @Override
    public boolean existsByIdAndStatus(long id, Status status) {
        return productRepo.existsByIdAndStatus(id, status.name());
    }

    @Override
    public boolean existsBySlug(String slug) {
        return productRepo.existsBySlug(slug);
    }

    @Override
    public boolean existsBySlugAndStatus(String slug, Status status) {
        return productRepo.existsBySlugAndStatus(slug, status.name());
    }

    @Override
    public boolean existsByCategoryId(int categoryId) {
        return productRepo.existsByCategory_Id(categoryId);
    }

    @Override
    public long countByIdNotAndCategory(long productId, int categoryId) {
        return productRepo.countByIdNotAndCategory_IdAndStatus(productId, categoryId, Product.Status.PUBLISHED.name());
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

            var product = ProductMapper.toDomain(entity, imageUrl);

            if (entity.isWithVariant()) {
                var variants = entity.getVariants().stream().map(e -> ProductMapper.toVariant(e, objectMapper))
                        .collect(Collectors.toList());

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
            var product = ProductMapper.toDomain(entity, imageUrl);

            if (entity.isWithVariant()) {
                var variants = entity.getVariants().stream().map(e -> ProductMapper.toVariant(e, objectMapper))
                        .collect(Collectors.toList());

                product.setVariants(variants);
            }

            return product;

        }

        return null;
    }

    @Override
    public List<Product> findProductHints(String q, int limit) {
        // String ql = "%" + q + "%";
        // return productRepo.findProductHints(ql, ql, PageRequest.of(0,
        // limit)).stream()
        // .map(e -> ProductMapper.toDomainCompat(e, imageUrl))
        // .collect(Collectors.toList());

        return productSearchDao.getHints(q, limit);
    }

    @Override
    public List<String> findProductBrandsByCategory(String categorySlug) {
        // return
        // productRepo.findDistinctBrands(categorySlug).stream().map(ProductBrandView::getBrand)
        // .collect(Collectors.toList());

        return productSearchDao.getProductBrands(categorySlug);
    }

    @Override
    public List<Product> getRelatedProducts(long productId, int categoryId, PageQuery pageQuery) {
        String status = Product.Status.PUBLISHED.name();
        var pageable = PageRequest.of(pageQuery.getPage(), pageQuery.getSize());
        return productRepo.findByIdNotAndCategory_IdAndStatus(productId, categoryId, status, pageable)
                .map(e -> ProductMapper.toDomainCompat(e, imageUrl))
                .toList();
    }

    @Override
    public PageData<Product> findAll(ProductQuery query) {
        Specification<ProductEntity> spec = null;

        if (query.getShopId() != null && query.getShopId() > 0) {
            Specification<ProductEntity> shopSpec = new BasicSpecification<>(
                    new SearchCriteria("id", Operator.EQUAL, query.getShopId(), "shop"));
            spec = Specification.where(shopSpec);
        }

        if (query.getDiscountId() != null && query.getDiscountId() > 0) {
            Specification<ProductEntity> discountSpec = new BasicSpecification<>(
                    new SearchCriteria("id", Operator.EQUAL, query.getDiscountId(), "discount"));
            spec = Specification.where(discountSpec);
        }

        if (StringUtils.hasText(query.getCategorySlug())) {
            Specification<ProductEntity> categorySpec = new BasicSpecification<>(
                    new SearchCriteria("slug", Operator.EQUAL, query.getCategorySlug(), "category"));
            spec = spec != null ? spec.and(categorySpec) : Specification.where(categorySpec);
        }

        if (query.getStatus() != null) {
            Specification<ProductEntity> statusSpec = new BasicSpecification<>(
                    new SearchCriteria("status", Operator.EQUAL, query.getStatus().name()));

            spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
        }

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ProductEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, q));
            Specification<ProductEntity> brandSpec = new BasicSpecification<>(
                    new SearchCriteria("brand", Operator.LIKE, q));
            spec = spec != null ? spec.and(nameSpec.or(brandSpec)) : Specification.where(nameSpec.or(brandSpec));
        }

        // String columnKey = "category_id";

        // if (category.getLevel() == 1) {
        // columnKey = "mainCategoryId";
        // } else if (category.getLevel() == 2) {
        // columnKey = "subCategoryId";
        // }

        // Specification<ProductEntity> catgorySpec = new BasicSpecification<>(
        // new SearchCriteria(columnKey, Operator.EQUAL, category.getId()));
        // spec = spec != null ? spec.and(catgorySpec) :
        // Specification.where(catgorySpec);
        // }

        if (query.getBrands() != null && query.getBrands().length > 0) {
            Specification<ProductEntity> brandSpec = new BasicSpecification<>(
                    new SearchCriteria("brand", Operator.IN, Arrays.asList(query.getBrands())));
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

        // var sort = Sort.by(Order.desc("createdAt"));

        // var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE,
        // sort);

        // var pageResult = productRepo.findAll(spec, pageable);

        // return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e,
        // imageUrl));

        return productSearchDao.getProducts(query);
    }

}
