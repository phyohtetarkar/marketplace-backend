package com.marketplace.domain.product.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.marketplace.domain.Constants;
import com.marketplace.domain.PageData;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchCriteria;
import com.marketplace.domain.common.SearchCriteria.Operator;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductQuery;
import com.marketplace.domain.product.dao.ProductDao;

@Component
public class GetAllProductUseCase {

	@Autowired
    private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;

	@Transactional(readOnly = true)
    public PageData<Product> apply(ProductQuery query) {
    	var sq = new SearchQuery();
    	
    	if (query.getShopId() != null && query.getShopId() > 0) {
            var c = SearchCriteria.simple("shop.id", Operator.EQUAL, query.getShopId());
			sq.addCriteria(c);
        }

        if (query.getDiscountId() != null && query.getDiscountId() > 0) {
            var c = SearchCriteria.simple("discount.id", Operator.EQUAL, query.getDiscountId());
			sq.addCriteria(c);
        }

        if (query.getCategoryId() != null && query.getCategoryId() > 0) {
        	var category = categoryDao.findById(query.getCategoryId());
        	if (category != null) {
        		var cLft = SearchCriteria.join("lft", Operator.GREATER_THAN_EQ, category.getLft(), "category");
        		var cRgt = SearchCriteria.join("rgt", Operator.LESS_THAN_EQ, category.getRgt(), "category");
    			sq.addCriteria(cLft);
    			sq.addCriteria(cRgt);
        	}
        }
        
        if (query.getFeatured() == Boolean.TRUE) {
			var c = SearchCriteria.simple("featured", Operator.EQUAL, Boolean.TRUE);
			sq.addCriteria(c);
		}
        
        if (query.getNewArrival() == Boolean.TRUE) {
        	var c = SearchCriteria.simple("newArrival", Operator.EQUAL, Boolean.TRUE);
        	sq.addCriteria(c);
        }

        if (StringUtils.hasText(query.getQ())) {
            var q = "%" + query.getQ().toLowerCase() + "%";
            var c = SearchCriteria.simple("name", Operator.LIKE, q);
            sq.addCriteria(c);
        }

        if (query.getBrands() != null && query.getBrands().length > 0) {
            var c = SearchCriteria.in("brand", (Object[]) query.getBrands());
            sq.addCriteria(c);
        }

        if (query.getDiscount() == Boolean.TRUE) {
            var c = SearchCriteria.notNull("discount");
            sq.addCriteria(c);
        }

        if (query.getMaxPrice() != null) {
            var c = SearchCriteria.simple("price", Operator.LESS_THAN_EQ, query.getMaxPrice());
            sq.addCriteria(c);
        }

        if (query.getStatus() != null) {
            var c = SearchCriteria.simple("status", Operator.EQUAL, query.getStatus());
            sq.addCriteria(c);
        }
        
        var deletedCriteria = SearchCriteria.simple("deleted", Operator.EQUAL, Boolean.FALSE);
        
        var shopExpireCriteria = SearchCriteria.join("expiredAt", Operator.GREATER_THAN_EQ, System.currentTimeMillis(), "shop");
        
        sq.addCriteria(deletedCriteria);
        sq.addCriteria(shopExpireCriteria);
        
        sq.setPageQuery(PageQuery.of(query.getPage(), Constants.PAGE_SIZE));
    	
        return productDao.findAll(sq);
    }

}
