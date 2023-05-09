package com.shoppingcenter.domain.order.usecase;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.discount.Discount.Type;
import com.shoppingcenter.domain.order.CreateOrderInput;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.Order.PaymentMethod;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.OrderItem;
import com.shoppingcenter.domain.order.dao.OrderDao;
import com.shoppingcenter.domain.order.dao.OrderItemDao;
import com.shoppingcenter.domain.product.dao.ProductDao;
import com.shoppingcenter.domain.product.dao.ProductVariantDao;
import com.shoppingcenter.domain.shop.dao.ShopDao;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;
import com.shoppingcenter.domain.user.UserDao;

import lombok.Setter;

@Setter
public class CreateOrderUseCase {
	
	private OrderDao orderDao;
	
	private OrderItemDao orderItemDao;
	
	private CartItemDao cartItemDao;
	
	private ShopDao shopDao;
	
	private UserDao userDao;
	
	private ProductDao productDao;
	
	private ProductVariantDao productVariantDao;
	
	private FileStorageAdapter fileStorageAdapter;

	public String apply(CreateOrderInput data) {
		
		var shop = shopDao.findById(data.getShopId());
		
		if (shop == null) {
			throw new ApplicationException("Shop not found");
		}	
		
		var user = userDao.findById(data.getUserId());
		
		if (user == null) {
			throw new ApplicationException("User not found");
		}
		
		var payment = data.getPayment();
		var delivery = data.getDelivery();
		
		if (data.getPaymentMethod() == null) {
			throw new ApplicationException("Invalid payment method");
		} else if (data.getPaymentMethod() == PaymentMethod.BANK_TRANSFER && payment == null) {
			throw new ApplicationException("Invalid payment info");
		}
		
		if (delivery == null) {
			throw new ApplicationException("Invalid delivery info");
		}
		
		delivery.validate();
		
		//var cartItems = cartItemDao.findByUser(data.getUserId(), data.getCartItems());
		
		//var cartItems = data.getCartItems();
		
//		if (cartItems == null || cartItems.isEmpty()) {
//			throw new ApplicationException("Invalid order items");
//		}
//		
//		if (cartItems.size() != data.getCartItems().size()) {
//			throw new ApplicationException("Invalid order items");
//		}
		
		var orderItems = new ArrayList<OrderItem>();
		
		var subTotalPrice = BigDecimal.valueOf(0);
		var totalPrice = BigDecimal.valueOf(0);
		var discount = BigDecimal.valueOf(0);
		var quantity = 0;
		
		for (var itemId : data.getCartItems()) {
			var item = cartItemDao.findById(itemId != null ? itemId : 0);
			
			if (item == null) {
				throw new ApplicationException("Invalid order items");
			}
			
			if (item.getProduct().getShop().getId() != data.getShopId()) {
				throw new ApplicationException("Invalid order items");
			}
			
			var orderItem = new OrderItem();
			orderItem.setProductId(item.getProduct().getId());
			orderItem.setProductSlug(item.getProduct().getSlug());
			orderItem.setProductName(item.getProduct().getName());
			orderItem.setProductImage(item.getProduct().getThumbnail());
			orderItem.setQuantity(item.getQuantity());
			
			if (item.getVariant() != null) {
				orderItem.setUnitPrice(item.getVariant().getPrice());
				orderItem.setAttributes(item.getVariant().getAttributes());
				
				var stockLeft = item.getVariant().getStockLeft();
				
				if (stockLeft - orderItem.getQuantity() < 0) {
					throw new ApplicationException(orderItem.getProductName() + " left only " + stockLeft + " items");
				}
				
				productVariantDao.updateStockLeft(item.getVariant().getId(), stockLeft - orderItem.getQuantity());
			} else {
				orderItem.setUnitPrice(item.getProduct().getPrice());
			}
			
			var stockLeft = item.getProduct().getStockLeft();
			if (stockLeft - orderItem.getQuantity() < 0) {
				throw new ApplicationException(orderItem.getProductName() + " left only " + stockLeft + " items");
			}
			productDao.updateStockLeft(orderItem.getProductId(), stockLeft - orderItem.getQuantity());
			
			var pd = item.getProduct().getDiscount();
			
			if (pd != null) {
				var d = pd.getType() == Type.FIXED_AMOUNT ? pd.getValue() : orderItem.getUnitPrice().multiply(pd.getValue()).divide(BigDecimal.valueOf(100));
				orderItem.setDiscount(d);
			} else {
				orderItem.setDiscount(BigDecimal.valueOf(0));
			}
			
			orderItems.add(orderItem);
			
			subTotalPrice = subTotalPrice.add(orderItem.getSubTotalPrice());
			totalPrice = totalPrice.add(orderItem.getTotalPrice());
			discount = discount.add(orderItem.getDiscount());
			quantity += orderItem.getQuantity();
			
		}
		
		var orderCode = generateOrderCode(data.getUserId());
		
		var order = new Order();
		order.setOrderCode(orderCode.toUpperCase());
		order.setStatus(Status.PENDING);
		order.setShop(shop);
		order.setUser(user);
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscount(discount);
		order.setQuantity(quantity);
		order.setNote(data.getNote());
		order.setPaymentMethod(data.getPaymentMethod());
		
		var orderId = orderDao.create(order);
		
		orderItems.forEach(item -> {
			item.setOrderId(orderId);
		});
		
		orderItemDao.createAll(orderItems);
		
		
		if (data.getPaymentMethod() == PaymentMethod.BANK_TRANSFER && payment != null) {
			if (payment.getFile() != null && !payment.getFile().isEmpty()) {
				var name = "transfer_payslip_" + orderId + "." + payment.getFile().getExtension();
				var dir = Constants.IMG_SHOP_ROOT + File.separator + data.getShopId() +  File.separator + Constants.IMG_ORDER_ROOT;
				fileStorageAdapter.write(payment.getFile(), dir, name);
			}
			
			payment.setOrderId(orderId);
			orderDao.savePaymentDetail(payment);
		}
		
		delivery.setOrderId(orderId);
		orderDao.saveDeliveryDetail(delivery);
		
		cartItemDao.deleteAll(data.getCartItems());
		
		return orderCode;
	}
	
	private String generateOrderCode(long userId) {
		var randomChars = UUID.randomUUID().toString().substring(0, 8);
		var customerId = "CUS" + userId;
		
		var orderCode = customerId + ":" + randomChars;
		
		return orderDao.existsByCode(orderCode.toUpperCase()) ? generateOrderCode(userId) : orderCode.toUpperCase();
	}
}
