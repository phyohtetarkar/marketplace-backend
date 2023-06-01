package com.shoppingcenter.data.order;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.order.DeliveryDetail;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.Order.Status;
import com.shoppingcenter.domain.order.OrderQuery;
import com.shoppingcenter.domain.order.PaymentDetail;
import com.shoppingcenter.domain.order.dao.OrderDao;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private DeliveryDetailRepo deliveryDetailRepo;
	
	@Autowired
	private PaymentDetailRepo paymentDetailRepo;
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public long create(Order order) {
		var entity = new OrderEntity();
		entity.setOrderCode(order.getOrderCode());
		entity.setSubTotalPrice(order.getSubTotalPrice());
		entity.setTotalPrice(order.getTotalPrice());
		entity.setQuantity(order.getQuantity());
		entity.setDiscount(order.getDiscount());
		entity.setStatus(order.getStatus());
		entity.setPaymentMethod(order.getPaymentMethod());
		entity.setNote(order.getNote());
		
		entity.setUserId(order.getUserId());
		entity.setShopId(order.getShopId());
		
		var result = orderRepo.save(entity);
		
		return result.getId();
	}
	
	@Override
	public void update(Order order) {
		var entity = orderRepo.findById(order.getId()).orElseGet(OrderEntity::new);
		entity.setSubTotalPrice(order.getSubTotalPrice());
		entity.setTotalPrice(order.getTotalPrice());
		entity.setQuantity(order.getQuantity());
		entity.setDiscount(order.getDiscount());
		entity.setStatus(order.getStatus());
		entity.setPaymentMethod(order.getPaymentMethod());
		entity.setNote(order.getNote());
		
		orderRepo.save(entity);
	}

	@Override
	public void updateStatus(long id, Status status) {
		orderRepo.updateStatus(id, status);
	}
	
	@Override
	public void saveDeliveryDetail(DeliveryDetail detail) {
		var entity = deliveryDetailRepo.findById(detail.getOrderId()).orElseGet(DeliveryDetailEntity::new);
		entity.setOrder(orderRepo.getReferenceById(detail.getOrderId()));
		entity.setName(detail.getName());
		entity.setPhone(detail.getPhone());
		entity.setCity(detail.getCity());
		entity.setAddress(detail.getAddress());
		
		deliveryDetailRepo.save(entity);
	}
	
	@Override
	public void savePaymentDetail(PaymentDetail detail) {
		var entity = paymentDetailRepo.findById(detail.getOrderId()).orElseGet(PaymentDetailEntity::new);
		entity.setAccountType(detail.getAccountType());
		entity.setReceiptImage(detail.getReceiptImage());
		entity.setOrder(orderRepo.getReferenceById(detail.getOrderId()));
		
		paymentDetailRepo.save(entity);
		
	}
	
	@Override
	public void updateReceiptImage(long orderId, String receiptImage) {
		paymentDetailRepo.updateReceiptImage(orderId, receiptImage);
	}

	@Override
	public boolean existsById(long id) {
		return orderRepo.existsById(id);
	}

	@Override
	public boolean existsByCode(String code) {
		return orderRepo.existsByOrderCode(code);
	}

	@Override
	public long getPendingOrderCountByShop(long shopId) {
		return orderRepo.countByShopIdAndStatus(shopId, Order.Status.PENDING);
	}
	
	@Override
	public long getOrderCountByShop(long shopId) {
		return orderRepo.countByShopId(shopId);
	}

	@Override
	public Order findById(long id) {
		return orderRepo.findById(id).map(e -> {
			var order = OrderMapper.toDomain(e);
			order.setUser(userRepo.findById(e.getUserId()).map(UserMapper::toDomain).orElse(null));
			order.setShop(shopRepo.findById(e.getShopId()).map(ShopMapper::toDomainCompat).orElse(null));
			return order;
		}).orElse(null);
	}

	@Override
	public Order findByCode(String code) {
		return orderRepo.findByOrderCode(code).map(e -> {
			var order = OrderMapper.toDomain(e);
			order.setUser(userRepo.findById(e.getUserId()).map(UserMapper::toDomain).orElse(null));
			order.setShop(shopRepo.findById(e.getShopId()).map(ShopMapper::toDomainCompat).orElse(null));
			return order;
		}).orElse(null);
	}

	@Override
	public PageData<Order> find(OrderQuery query) {
		Specification<OrderEntity> spec = null;
		
		if (query.getShopId() != null && query.getShopId() > 0) {
            Specification<OrderEntity> shopSpec = new BasicSpecification<>(
                    new SearchCriteria("shopId", Operator.EQUAL, query.getShopId()));
            spec = Specification.where(shopSpec);
        }
		
		if (query.getUserId() != null && query.getUserId() > 0) {
            Specification<OrderEntity> userSpec = new BasicSpecification<>(
                    new SearchCriteria("userId", Operator.EQUAL, query.getUserId()));
            spec = spec != null ? spec.and(userSpec) : Specification.where(userSpec);
        }
		
		if (query.getStatus() != null) {
			Specification<OrderEntity> statusSpec = new BasicSpecification<>(
                    new SearchCriteria("status", Operator.EQUAL, query.getStatus()));
			spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
		}
		
		if (StringUtils.hasText(query.getCode())) {
			Specification<OrderEntity> codeSpec = new BasicSpecification<>(
                    new SearchCriteria("orderCode", Operator.EQUAL, query.getCode()));
			spec = spec != null ? spec.and(codeSpec) : Specification.where(codeSpec);
		}
		
		if (StringUtils.hasText(query.getDate())) {
			var date = LocalDate.parse(query.getDate());
			ZoneId zoneId = ZoneOffset.UTC;
			
			try {
				zoneId = StringUtils.hasText(query.getTimeZone()) ? ZoneId.of(query.getTimeZone()): ZoneOffset.UTC;
			} catch (Exception e) {
			}
			
			var from = date.atStartOfDay(zoneId).toInstant().toEpochMilli();
			var to = date.atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli();
			
			Specification<OrderEntity> dateFromSpec = new BasicSpecification<>(
                    new SearchCriteria("createdAt", Operator.GREATER_THAN_EQ, from));
			Specification<OrderEntity> dateToSpec = new BasicSpecification<>(
                    new SearchCriteria("createdAt", Operator.LESS_THAN_EQ, to));
			
			var dateSpec = dateFromSpec.and(dateToSpec);
 			
			spec = spec != null ? spec.and(dateSpec) : Specification.where(dateSpec);
		}
		
		var sort = Sort.by(Direction.DESC, "createdAt");

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);
		
        var pageResult = orderRepo.findAll(spec, pageable);
		
        return PageDataMapper.map(pageResult, OrderMapper::toDomainCompat);
	}


}
