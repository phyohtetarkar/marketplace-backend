package com.shoppingcenter.app.controller.order;

import org.hibernate.StaleObjectStateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.MultipartFileMapper;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.order.dto.OrderCreateDTO;
import com.shoppingcenter.app.controller.order.dto.OrderDTO;
import com.shoppingcenter.domain.order.CreateOrderInput;
import com.shoppingcenter.domain.order.OrderQuery;
import com.shoppingcenter.domain.order.usecase.CancelOrderUseCase;
import com.shoppingcenter.domain.order.usecase.CompleteOrderUseCase;
import com.shoppingcenter.domain.order.usecase.ConfirmOrderUseCase;
import com.shoppingcenter.domain.order.usecase.CreateOrderUseCase;
import com.shoppingcenter.domain.order.usecase.GetAllOrderByQueryUseCase;
import com.shoppingcenter.domain.order.usecase.GetOrderByCodeUseCase;
import com.shoppingcenter.domain.order.usecase.MarkOrderItemAsRemovedUseCase;
import com.shoppingcenter.domain.order.usecase.UploadReceiptImageUseCase;

@Facade
public class OrderFacade {
	
	@Autowired
	private CreateOrderUseCase createOrderUseCase;
	
	@Autowired
	private MarkOrderItemAsRemovedUseCase markOrderItemAsRemovedUseCase;
	
	@Autowired
	private ConfirmOrderUseCase confirmOrderUseCase;
	
	@Autowired
	private CompleteOrderUseCase completeOrderUseCase;
	
	@Autowired
	private CancelOrderUseCase cancelOrderUseCase;
	
	@Autowired
	private GetAllOrderByQueryUseCase getAllOrderByQueryUseCase;
	
	@Autowired
	private GetOrderByCodeUseCase getOrderByCodeUseCase;
	
	@Autowired
	private UploadReceiptImageUseCase uploadReceiptImageUseCase;
	
	@Autowired
	private ModelMapper modelMapper;

	@Retryable(retryFor = { StaleObjectStateException.class })
	@Transactional
	public String createOrder(OrderCreateDTO data) {
		return createOrderUseCase.apply(modelMapper.map(data, CreateOrderInput.class));
	}
	
	@Transactional
	public void removeOrderItem(long userId, long itemId) {
    	markOrderItemAsRemovedUseCase.apply(userId, itemId);
    }
	
	@Transactional
	public void confirmOrder(long userId, long orderId) {
		confirmOrderUseCase.apply(userId, orderId);
	}
	
	@Retryable(retryFor = { StaleObjectStateException.class })
	@Transactional
	public void completeOrder(long userId, long orderId) {
		completeOrderUseCase.apply(userId, orderId);
	}
	
	@Transactional
	public void cancelOrder(long userId, long orderId) {
		cancelOrderUseCase.apply(userId, orderId);
	}
	
	@Transactional
	public void uploadReceiptImage(long userId, long orderId, MultipartFile file) {
		var uploadFile = MultipartFileMapper.toUploadFile(file);
		uploadReceiptImageUseCase.apply(userId, orderId, uploadFile);
	}
	
	@Transactional(readOnly = true)
	public OrderDTO getOrderByCode(String code) {
		var result = getOrderByCodeUseCase.apply(code);
		
		return result != null ? modelMapper.map(result, OrderDTO.class) : null;
	}
	
	@Transactional(readOnly = true)
	public PageDataDTO<OrderDTO> getOrders(OrderQuery query) {
		return modelMapper.map(getAllOrderByQueryUseCase.apply(query), OrderDTO.pageType());
	}
}
