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
	
	private GenerateLftRgtUseCase generateLftRgtUseCase;

	public void apply(Category category, UploadFile file) {
		
		var old = category.getId() > 0 ? dao.findById(category.getId()) : null;
		
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
			String suffix = file.getExtension();
			String imageName = String.format("%d.%s", result.getId(), suffix);

			String dir = Constants.IMG_CATEGORY_ROOT;
			fileStorageAdapter.write(file, dir, imageName);

			var oldImage = result.getImage();

			if (Utils.hasText(oldImage) && !oldImage.equals(imageName)) {
				fileStorageAdapter.delete(dir, oldImage);
			}

			dao.updateImage(result.getId(), imageName);
		}
		
		if (old == null || old.getCategoryId() != result.getCategoryId()) {
			System.out.println("Generate LFT RGT");
			generateLftRgtUseCase.apply();
		}
		
	}

}
