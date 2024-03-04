package com.marketplace.api.consumer.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.MultipartFileConverter;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.domain.order.usecase.CancelOrderByBuyerUseCase;
import com.marketplace.domain.order.usecase.CreateOrderUseCase;
import com.marketplace.domain.order.usecase.GetOrderByCodeUseCase;
import com.marketplace.domain.order.usecase.UploadReceiptImageUseCase;

@Component
public class OrderControllerFacade {

	@Autowired
	private CreateOrderUseCase createOrderUseCase;
	
	@Autowired
	private CancelOrderByBuyerUseCase cancelOrderByBuyerUseCase;
	
	@Autowired
	private UploadReceiptImageUseCase uploadReceiptImageUseCase;
	
	@Autowired
	private GetOrderByCodeUseCase getOrderByCodeUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;
	
	public String createOrder(OrderCreateDTO values) {
		return createOrderUseCase.apply(mapper.map(values));
	}
	
	public void cancelOrder(long userId, long orderId) {
		cancelOrderByBuyerUseCase.apply(userId, orderId);
	}
	
	public void uploadReceiptImage(long userId, long orderId, MultipartFile file) {
		var uploadFile = MultipartFileConverter.toUploadFile(file);
		uploadReceiptImageUseCase.apply(userId, orderId, uploadFile);
	}
	
	public OrderDTO getOrderByCode(String code) {
		var source = getOrderByCodeUseCase.apply(code);
		return mapper.map(source);
	}
}
