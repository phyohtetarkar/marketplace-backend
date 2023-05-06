package com.shoppingcenter.data.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.shoppingcenter.data.product.view.ProductBrandView;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.PageQuery;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductAttribute;
import com.shoppingcenter.domain.product.ProductEditInput;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.product.dao.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    private ProductAttributeRepo productAttributeRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public long save(ProductEditInput data) {
        var entity = productRepo.findById(data.getId()).orElseGet(ProductEntity::new);

        // if (entity.getId() == 0 || !Utils.equalsIgnoreCase(entity.getName(),
        // input.getName())) {
        // String prefix = input.getSlug().replaceAll("\\s+", "-").toLowerCase();
        // String slug = Utils.generateSlug(prefix, productRepo::existsBySlug);
        // entity.setSlug(slug);
        // }

        entity.setName(data.getName());
        entity.setSlug(data.getSlug());
        entity.setBrand(data.getBrand());
        entity.setPrice(data.getPrice());
        entity.setStockLeft(data.getStockLeft());
        entity.setSku(data.getSku());
        entity.setNewArrival(data.isNewArrival());
        entity.setDescription(data.getDescription());
        entity.setHidden(data.isHidden());
        entity.setThumbnail(data.getThumbnail());
        entity.setVideoUrl(data.getVideoUrl());      

        entity.setShop(shopRepo.getReferenceById(data.getShopId()));

        entity.setCategory(categoryRepo.getReferenceById(data.getCategoryId()));

        entity.setWithVariant(data.isWithVariant());
        
        var isNew = entity.getId() <= 0;
        
        var result = productRepo.save(entity);
        
        if (isNew && data.getAttributes() != null) {
        	var attributes = data.getAttributes().stream().map(a -> {
        		var en = new ProductAttributeEntity();
        		en.setId(a.getId());
        		en.setName(a.getName());
        		en.setSort(a.getSort());
        		en.setProduct(result);
        		return en;
        	}).toList();
        	
        	productAttributeRepo.saveAll(attributes);
        }
        
        var slug = result.getSlug() + "-" + result.getId();
        
        productRepo.updateSlug(result.getId(), slug);

        return result.getId();
    }

    @Override
    public void updateThumbnail(long id, String thumbnail) {
        productRepo.updateThumbnail(id, thumbnail);
    }
    
    @Override
    public void updateStockLeft(long id, int stockLeft) {
    	var entity = productRepo.getReferenceById(id);
    	entity.setStockLeft(stockLeft);
    }
    
    @Override
    public void decreaseStockLeft(long id, int amount) {
    	var entity = productRepo.getReferenceById(id);
    	var stockLeft = entity.getStockLeft() - amount;
    	if (stockLeft < 0) {
    		throw new ApplicationException("Not enough stock");
    	}
    	
    	entity.setStockLeft(stockLeft);
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
        return productRepo.findById(id).map(ProductMapper::toDomain).orElse(null);
    }

    @Override
    public Product findBySlug(String slug) {
        return productRepo.findBySlug(slug).map(ProductMapper::toDomain).orElse(null);
    }
    
    @Override
    public List<ProductAttribute> getProductAttributes(long productId) {
    	return productAttributeRepo.findByProductId(productId).stream().map(e -> {
    		var a = new ProductAttribute();
    		a.setId(e.getId());
    		a.setName(e.getName());
    		a.setSort(e.getSort());
    		return a;
    	}).toList();
    }

    @Override
    public List<Product> findProductHints(String q, int limit) {
        String ql = "%" + q + "%";
        return productRepo.findProductHints(ql, ql, PageRequest.of(0, limit)).stream()
                .map(e -> ProductMapper.toDomainCompat(e)).toList();
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
                .map(e -> ProductMapper.toDomainCompat(e)).toList();
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
        	var category = categoryRepo.findById(query.getCategoryId()).orElse(null);
        	if (category != null) {
        		var categoryLftSpec = new BasicSpecification<ProductEntity>(new SearchCriteria("lft", Operator.GREATER_THAN_EQ, category.getLft(), "category"));
        		var categoryRgtSpec = new BasicSpecification<ProductEntity>(new SearchCriteria("rgt", Operator.LESS_THAN_EQ, category.getRgt(), "category"));
                var categorySpec = categoryLftSpec.and(categoryRgtSpec);
        		spec = spec != null ? spec.and(categorySpec) : Specification.where(categorySpec);
        	}
        }

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ProductEntity> nameSpec = new BasicSpecification<>(
                    new SearchCriteria("name", Operator.LIKE, "%" + q + "%"));
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
        
        Specification<ProductEntity> shopExpiredSpec = new BasicSpecification<>(
                new SearchCriteria("expired", Operator.EQUAL, false, "shop"));
        spec = spec != null ? spec.and(shopExpiredSpec) : Specification.where(shopExpiredSpec);
        
        Specification<ProductEntity> shopDisabledSpec = new BasicSpecification<>(
                new SearchCriteria("disabled", Operator.EQUAL, false, "shop"));
        spec = spec != null ? spec.and(shopDisabledSpec) : Specification.where(shopDisabledSpec);

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = productRepo.findAll(spec, pageable);

        return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e));
    }

    @SuppressWarnings("unused")
	private void visitCategory(CategoryEntity category, Set<Integer> list) {
        list.add(category.getId());
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
