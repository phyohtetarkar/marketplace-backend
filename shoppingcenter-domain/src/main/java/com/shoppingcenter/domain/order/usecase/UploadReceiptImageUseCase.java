package com.shoppingcenter.domain.order.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.common.FileStorageAdapter;
import com.shoppingcenter.domain.order.dao.OrderDao;

import lombok.Setter;

@Setter
public class UploadReceiptImageUseCase {

	private OrderDao orderDao;

	private FileStorageAdapter fileStorageAdapter;

	public void apply(long userId, long orderId, UploadFile file) {
		var order = orderDao.findById(orderId);

		if (order == null) {
			throw new ApplicationException("Order not found");
		}

		if (order.getUser().getId() != userId) {
			throw new ApplicationException("Order not found");
		}

		if (file == null || file.isEmpty()) {
			throw new ApplicationException("Required image file");
		}

		var fileSize = file.getSize() / (1024.0 * 1024.0);

		if (fileSize > 0.6) {
			throw new ApplicationException("File size must not greater than 600KB");
		}

		var oldImage = order.getPayment().getReceiptImage();

		var extension = file.getExtension();
		var imageName = String.format("%d-%d-transfer-receipt.%s", order.getShop().getId(), orderId, extension);
		var dir = Constants.IMG_ORDER_ROOT;

		orderDao.updateReceiptImage(orderId, imageName);

		fileStorageAdapter.write(file, dir, imageName);

		if (Utils.hasText(oldImage) && !oldImage.equals(imageName)) {
			fileStorageAdapter.delete(dir, oldImage);
		}
	}

}
