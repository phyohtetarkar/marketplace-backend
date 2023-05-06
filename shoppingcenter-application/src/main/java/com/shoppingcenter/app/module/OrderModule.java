package com.shoppingcenter.app.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.dao.OrderItemDao;
import com.shoppingcenter.domain.order.usecase.GetAllOrderByQueryUseCase;
import com.shoppingcenter.domain.order.usecase.GetOrderByCodeUseCase;
import com.shoppingcenter.domain.order.usecase.GetPendingOrderCountByShopUseCase;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;
import com.shoppingcenter.domain.user.UserDao;

@Configuration
public class OrderModule {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	@Autowired
	private CartItemDao cartItemDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductVariantDao productVariantDao;
	
	@Autowired
	private FileStorageAdapter fileStorageAdapter;
	
	@Bean
    GetPendingOrderCountByShopUseCase getPendingOrderCountByShopUseCase() {
    	return new GetPendingOrderCountByShopUseCase(orderDao);
    }
	
	@Bean
	GetOrderByCodeUseCase getOrderByCodeUseCase() {
		return new GetOrderByCodeUseCase(orderDao);
	}
	
	@Bean
	GetAllOrderByQueryUseCase getAllOrderByQueryUseCase() {
		return new GetAllOrderByQueryUseCase(orderDao);
	}
	
}
