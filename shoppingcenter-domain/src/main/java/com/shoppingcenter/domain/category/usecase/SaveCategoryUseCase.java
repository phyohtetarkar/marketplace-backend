package com.shoppingcenter.domain.category.usecase;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

import lombok.Setter;

@Setter
public class SaveCategoryUseCase {

	private CategoryDao dao;

	private FileStorageAdapter fileStorageAdapter;

	public void apply(Category category, UploadFile file) {
		if (!Utils.hasText(category.getName())) {
			throw new ApplicationException("Required category name");
		}

		var slug = Utils.convertToSlug(category.getName());

		if (!Utils.hasText(slug)) {
			throw new ApplicationException("Invalid slug value");
		}

		category.setSlug(slug);

		if (category.getCategoryId() != null && !dao.existsById(category.getCategoryId())) {
			throw new ApplicationException("Parent category not found");
		}

		var result = dao.save(category);

		if (file != null) {
			var timestamp = System.currentTimeMillis();
			String suffix = file.getExtension();
			String imageName = String.format("%d_%d.%s", result.getId(), timestamp, suffix);

			String dir = Constants.IMG_CATEGORY_ROOT;
			fileStorageAdapter.write(file, dir, imageName);

			var old = result.getImage();

			if (Utils.hasText(old)) {
				fileStorageAdapter.delete(dir, old);
			}

			dao.updateImage(result.getId(), imageName);
		}
	}

}
