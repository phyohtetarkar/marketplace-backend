package com.marketplace.domain.order.usecase;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.discount.Discount.Type;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.Order.PaymentMethod;
import com.marketplace.domain.order.Order.Status;
import com.marketplace.domain.order.OrderCreateInput;
import com.marketplace.domain.order.OrderItem;
import com.marketplace.domain.order.OrderStatusHistory;
import com.marketplace.domain.order.dao.OrderDao;
import com.marketplace.domain.order.dao.OrderItemDao;
import com.marketplace.domain.order.dao.OrderStatusHistoryDao;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.shop.dao.ShopDao;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;
import com.marketplace.domain.user.dao.UserDao;

@Component
public class CreateOrderUseCase {
	
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
	private OrderStatusHistoryDao orderStatusHistoryDao;
	
	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Retryable(noRetryFor = { ApplicationException.class })
	@Transactional
	public String apply(OrderCreateInput values) {
		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}	
		
		if (!userDao.existsById(values.getUserId())) {
			throw new ApplicationException("User not found");
		}
		
		var expiredAt = shopDao.getExpiredAt(values.getShopId());
		
		if (expiredAt < System.currentTimeMillis()) {
			throw new ApplicationException("Shop not found");
		}
		
		var payment = values.getPayment();
		var delivery = values.getDelivery();
		
		if (values.getPaymentMethod() == null) {
			throw new ApplicationException("Invalid payment method");
		} else if (values.getPaymentMethod() == PaymentMethod.BANK_TRANSFER && payment == null) {
			throw new ApplicationException("Invalid payment info");
		}
		
		if (delivery == null) {
			throw new ApplicationException("Invalid delivery info");
		}
		
		delivery.validate();
		
		var orderItems = new ArrayList<OrderItem>();
		
		var subTotalPrice = BigDecimal.valueOf(0);
		var totalPrice = BigDecimal.valueOf(0);
		var discount = BigDecimal.valueOf(0);
		var quantity = 0;
		
		var removeItemList = new ArrayList<CartItem.ID>();
		
		for (var cartItem : values.getCartItems()) {
			var id = new CartItem.ID(values.getUserId(), cartItem.getProductId(), cartItem.getVariantId()); 
			var item = cartItemDao.findById(id);
			
			if (item == null) {
				throw new ApplicationException("Unable to submit order");
			}
			
			var product = item.getProduct();
			
			if (product.isDeleted() || !product.isAvailable() || product.getStatus() == Product.Status.DRAFT) {
				throw new ApplicationException("Product not available: " + product.getName());
			}
			
			if (product.getShop().getId() != values.getShopId()) {
				throw new ApplicationException("Unable to submit order");
			}
			
			var orderItem = new OrderItem();
			orderItem.setQuantity(item.getQuantity());
			orderItem.setProduct(product);
			
			if (item.getVariant() != null) {
				var variant = item.getVariant();
				orderItem.setProductVariant(variant);
				orderItem.setUnitPrice(variant.getPrice());
				
				if (!variant.isAvailable() || variant.isDeleted()) {
					throw new ApplicationException("Product not available: " + product.getName());
				}
				
			} else {
				orderItem.setUnitPrice(item.getProduct().getPrice());
				if (product.isDeleted() || !product.isAvailable()) {
					throw new ApplicationException("Product not available: " + product.getName());
				}
			}
			
			var pd = item.getProduct().getDiscount();
			
			if (pd != null) {
				var d = pd.getType() == Type.FIXED_AMOUNT ? pd.getValue() : orderItem.getUnitPrice().multiply(pd.getValue()).divide(BigDecimal.valueOf(100));
				if (d == null) {
					throw new ApplicationException("Unable to submit order");
				}
				orderItem.setDiscountPrice(d);
			} else {
				orderItem.setDiscountPrice(BigDecimal.ZERO);
			}
			
			orderItems.add(orderItem);
			
			subTotalPrice = subTotalPrice.add(orderItem.getSubTotalPrice());
			totalPrice = totalPrice.add(orderItem.getTotalPrice());
			discount = discount.add(orderItem.getDiscountPrice());
			quantity += orderItem.getQuantity();
			
			removeItemList.add(id);
		}
		
		//var orderCode = generateOrderCode(LocalDate.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		var orderCode = generateOrderCode();
		
		var order = new Order();
		order.setOrderCode(orderCode.toUpperCase());
		order.setStatus(Status.PENDING);
		order.setSubTotalPrice(subTotalPrice);
		order.setTotalPrice(totalPrice);
		order.setDiscountPrice(discount);
		order.setQuantity(quantity);
		order.setNote(values.getNote());
		order.setPaymentMethod(values.getPaymentMethod());
		
		var orderId = orderDao.create(values.getShopId(), values.getUserId(), order);
		orderItemDao.createAll(orderId, orderItems);
		
		if (values.getPaymentMethod() == PaymentMethod.BANK_TRANSFER && payment != null) {
			if (payment.getFile() != null && !payment.getFile().isEmpty()) {
				var fileSize = payment.getFile().getSize() / (1024.0 * 1024.0);
				
				if (fileSize > 0.512) {
					throw new ApplicationException("File size must not greater than 512KB");
				}
				
				var extension = payment.getFile().getExtension();
				var name = String.format("transfer-receipt-%d.%s", orderId, extension);
				var dir = Constants.IMG_ORDER_ROOT;
				fileStorageAdapter.write(payment.getFile(), dir, name);
				payment.setReceiptImage(name);
			}
			
			orderDao.savePaymentDetail(orderId, payment);
		}
		
		orderDao.saveDeliveryDetail(orderId, delivery);
		
		cartItemDao.deleteAll(removeItemList);
		
		var history = new OrderStatusHistory();
		history.setOrderId(orderId);
		history.setStatus(Status.PENDING);
		history.setRemark("Order created");
		
		orderStatusHistoryDao.save(history);
		
		return orderCode;
	}
	
	private String generateOrderCode() {
		var randomChars = Utils.generateRandomCode(5);
		
		var orderCode = randomChars.toUpperCase();
		var suffix = 0;
		
		while (orderDao.existsByCode(orderCode + suffix)) {
			suffix += 1;
		}
		
		return orderCode + suffix;
	}
}
