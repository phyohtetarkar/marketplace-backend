package com.shoppingcenter.core.product;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageResult;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.product.model.ProductVariant;
import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.product.ProductRepo;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Product findById(long id) {
        ProductEntity entity = productRepo.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
        Product product = Product.create(entity, baseUrl);
        product.setVariants(
                entity.getVariants().stream().map(e -> ProductVariant.create(e, mapper)).collect(Collectors.toList()));
        return product;
    }

    @Override
    public Product findBySlug(String slug) {
        ProductEntity entity = productRepo.findBySlug(slug)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
        Product product = Product.create(entity, baseUrl);
        product.setVariants(
                entity.getVariants().stream().map(e -> ProductVariant.create(e, mapper)).collect(Collectors.toList()));
        return product;
    }

    @Override
    public PageResult<Product> findAll(ProductQuery query) {
        Specification<ProductEntity> spec = null;

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ProductEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, q));
            Specification<ProductEntity> brandSpec = new BasicSpecification<>(
                    new SearchCriteria("brand", Operator.LIKE, q));
            spec = Specification.where(nameSpec).or(brandSpec);
        }

        if (StringUtils.hasText(query.getCategorySlug())) {
            CategoryEntity category = categoryRepo.findBySlug(query.getCategorySlug())
                    .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));

            String columnKey = "category_id";

            if (category.getLevel() == 1) {
                columnKey = "mainCategoryId";
            } else if (category.getLevel() == 2) {
                columnKey = "subCategoryId";
            }

            Specification<ProductEntity> catgorySpec = new BasicSpecification<>(
                    new SearchCriteria(columnKey, Operator.EQUAL, category.getId()));
            spec = spec != null ? spec.and(catgorySpec) : Specification.where(catgorySpec);
        }

        if (StringUtils.hasText(query.getBrand())) {
            Specification<ProductEntity> brandSpec = new BasicSpecification<>(
                    new SearchCriteria("brand", Operator.EQUAL, query.getBrand()));
            spec = spec != null ? spec.and(brandSpec) : Specification.where(brandSpec);
        }

        if (query.getDiscount() == true) {
            Specification<ProductEntity> discountSpec = new BasicSpecification<>(
                    new SearchCriteria("discount_id", Operator.NOT_EQ, null));
            spec = spec != null ? spec.and(discountSpec) : Specification.where(discountSpec);
        }

        if (query.getMaxPrice() != null) {
            Specification<ProductEntity> maxPriceSpec = new BasicSpecification<>(
                    new SearchCriteria("price", Operator.LESS_THAN_EQ, query.getMaxPrice()));
            spec = spec != null ? spec.and(maxPriceSpec) : Specification.where(maxPriceSpec);
        }

        Pageable pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE);

        Page<ProductEntity> pageResult = productRepo.findAll(spec, pageable);

        PageResult<Product> result = new PageResult<>();
        result.setContents(pageResult.map(e -> Product.createCompat(e, baseUrl)).toList());
        result.setCurrentPage(pageResult.getNumber());
        result.setTotalPage(pageResult.getTotalPages());
        result.setPageSize(pageResult.getNumberOfElements());
        return result;
    }
}
