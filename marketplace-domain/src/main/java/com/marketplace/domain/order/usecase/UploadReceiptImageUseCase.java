package com.marketplace.domain.order.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.Utils;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.order.PaymentDetail;
import com.marketplace.domain.order.dao.OrderDao;

@Component
public class UploadReceiptImageUseCase {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Transactional
	public void apply(long userId, long orderId, UploadFile file) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}

		if (order.getCustomer().getId() != userId) {
			throw new ApplicationException("Order not found");
		}

		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Required image file");
		}

		var fileSize = file.getSize() / (1024.0 * 1024.0);

		if (fileSize > 0.512) {
			throw new ApplicationException("File size must not greater than 512KB");
		}
		
		var oldImage = Optional.ofNullable(order.getPayment())
				.map(PaymentDetail::getReceiptImage)
				.orElse(null);

		var extension = file.getExtension();
		var dateTime = Utils.getCurrentDateTimeFormatted();
		var imageName = String.format("transfer-receipt-%d-%s.%s", orderId, dateTime, extension);
		var dir = Constants.IMG_ORDER_ROOT;

		orderDao.updateReceiptImage(orderId, imageName);

		fileStorageAdapter.write(file, dir, imageName);

		if (Utils.hasText(oldImage)) {
			fileStorageAdapter.delete(dir, oldImage);
		}
	}

}
