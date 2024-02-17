package com.marketplace.data.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.marketplace.data.JpaSpecificationBuilder;
import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.category.CategoryRepo;
import com.marketplace.data.discount.DiscountRepo;
import com.marketplace.data.product.view.ProductBrandView;
import com.marketplace.data.shop.ShopRepo;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductUpdateInput;
import com.marketplace.domain.product.dao.ProductDao;

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

    @Override
    public long create(ProductCreateInput values) {
        var entity = new ProductEntity();
        entity.setName(values.getName());
        entity.setSlug(values.getSlug());
        entity.setBrand(values.getBrand());
        entity.setPrice(values.getPrice());
        entity.setSku(values.getSku());
        entity.setAvailable(values.isAvailable());
        entity.setNewArrival(values.isNewArrival());
        entity.setDescription(values.getDescription());
        entity.setStatus(values.getStatus());
        entity.setThumbnail(values.getThumbnail());
        entity.setVideoEmbed(values.getVideoEmbed());
        entity.setWithVariant(values.isWithVariant());

        entity.setShop(shopRepo.getReferenceById(values.getShopId()));

        entity.setCategory(categoryRepo.getReferenceById(values.getCategoryId()));
        
        if (values.getDiscountId() != null) {
        	entity.setDiscount(discountRepo.getReferenceById(values.getDiscountId()));
        } else {
        	entity.setDiscount(null);      
        }
        
        var result = productRepo.save(entity);

        return result.getId();
    }
    
    @Override
    public long update(ProductUpdateInput values) {
    	var entity = productRepo.findById(values.getId()).orElseGet(ProductEntity::new);
        entity.setName(values.getName());
        entity.setSlug(values.getSlug());
        entity.setBrand(values.getBrand());
        entity.setPrice(values.getPrice());
        entity.setAvailable(values.isAvailable());
        entity.setSku(values.getSku());
        entity.setNewArrival(values.isNewArrival());
        entity.setCategory(categoryRepo.getReferenceById(values.getCategoryId()));
        
        if (values.getDiscountId() != null) {
        	entity.setDiscount(discountRepo.getReferenceById(values.getDiscountId()));
        } else {
        	entity.setDiscount(null);      
        }
        
        var result = productRepo.save(entity);

        return result.getId();
    }

    @Override
    public void updateThumbnail(long id, String thumbnail) {
        productRepo.updateThumbnail(id, thumbnail);
    }
    
    @Override
    public void removeDiscount(long discountId) {
        productRepo.removeDiscountAll(discountId);
    }

    @Override
    public void updateStatus(long id, Product.Status status) {
        productRepo.updateStatus(id, status);
    }
    
    @Override
    public void updateDeleted(long productId, boolean deleted) {
    	productRepo.updateDeleted(productId, deleted);
    }
    
    @Override
    public void updateFeatured(long productId, boolean featured) {
    	productRepo.updateFeatured(productId, featured);
    }
    
    @Override
    public void updatePrice(long productId, BigDecimal price) {
    	productRepo.updatePrice(productId, price);
    }
    
    @Override
    public void updateDescription(long productId, String value) {
    	productRepo.updateDescription(productId, value);
    }

    @Override
    public boolean existsById(long id) {
        return productRepo.existsByIdAndDeletedFalse(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return productRepo.existsBySlugAndDeletedFalse(slug);
    }

    @Override
    public boolean existsByCategory(int categoryId) {
        return productRepo.existsByCategory_IdAndDeletedFalse(categoryId);
    }
    
    @Override
	public boolean existsByIdAndShop(long id, long shopId) {
		return productRepo.existsByIdAndShop_IdAndDeletedFalse(id, shopId);
	}

	@Override
	public boolean existsByIdNotAndSlug(long id, String slug) {
		return productRepo.existsByIdNotAndSlug(id, slug);
	}
    
    @Override
    public long count() {
    	return productRepo.countByDeletedFalse();
    }

    @Override
    public long countByDiscount(long discountId) {
        return productRepo.countByDiscount_Id(discountId);
    }

    @Override
    public long countByShop(long shopId) {
        return productRepo.countByShop_IdAndDeletedFalse(shopId);
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
    public List<Product> findProductHints(String q, int limit) {
        String ql = "%" + q + "%";
        return productRepo.findProductHints(ql, ql, PageRequest.of(0, limit)).stream()
                .map(e -> ProductMapper.toDomainCompat(e)).toList();
    }
    
    @Override
    public List<String> findProductBrandsByQuery(String q) {
    	return productRepo.findDistinctBrandsByNameLike(q).stream()
        		.map(ProductBrandView::getBrand)
        		.toList();
    }

    @Override
    public List<String> findProductBrandsByCategory(int lft, int rgt) {
        return productRepo.findDistinctBrandsByCategory(lft, rgt).stream()
        		.map(ProductBrandView::getBrand)
        		.toList();
    }
    
    @Override
	public BigDecimal getMaxPriceByNameLike(String q) {
		return productRepo.getMaxPriceByNameLike(q);
	}

	@Override
	public BigDecimal getMaxPriceByCategory(int lft, int rgt) {
		return productRepo.getMaxPriceByCategory(lft, rgt);
	}

    @Override
    public List<Product> getRelatedProducts(long productId, PageQuery pageQuery) {
        var product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            return new ArrayList<>();
        }

        var pageable = PageQueryMapper.fromPageQuery(pageQuery);
        var status = Product.Status.PUBLISHED;
        return productRepo
                .findByIdNotAndCategoryIdAndStatusAndDeletedFalse(productId, product.getCategory().getId(), status,
                        pageable)
                .map(e -> ProductMapper.toDomainCompat(e)).toList();
    }
    
    @Override
    public List<Product> getTopFeaturedProducts() {
    	long currentTime = System.currentTimeMillis();
    	var status = Product.Status.PUBLISHED;
    	return productRepo.findTop12ByFeaturedTrueAndDeletedFalseAndStatusAndShop_ExpiredAtGreaterThanOrderByCreatedAtDesc(status, currentTime).stream()
    			.map(e -> ProductMapper.toDomainCompat(e)).toList();
    }
    
    @Override
    public List<Product> getTopDiscountProducts() {
    	long currentTime = System.currentTimeMillis();
    	var status = Product.Status.PUBLISHED;
    	return productRepo.findTop12ByDeletedFalseAndDiscountNotNullAndStatusAndShop_ExpiredAtGreaterThanOrderByCreatedAtDesc(status, currentTime).stream()
    			.map(e -> ProductMapper.toDomainCompat(e)).toList();
    }

    @Override
    public PageData<Product> findAll(SearchQuery searchQuery) {
    	var spec = JpaSpecificationBuilder.build(searchQuery.getCriterias(), ProductEntity.class);

        var pageable = PageQueryMapper.fromPageQuery(searchQuery.getPageQuery());

        var pageResult = spec != null ? productRepo.findAll(spec, pageable) : productRepo.findAll(pageable);

        return PageDataMapper.map(pageResult, e -> ProductMapper.toDomainCompat(e));
    }

}
