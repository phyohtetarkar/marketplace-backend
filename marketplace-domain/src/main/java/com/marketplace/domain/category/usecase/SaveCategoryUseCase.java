package com.marketplace.domain.category.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.Utils;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.category.CategoryInput;
import com.marketplace.domain.common.FileStorageAdapter;

@Component
public class SaveCategoryUseCase {

	@Autowired
	private CategoryDao dao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;
	
	@Autowired
	private GenerateLftRgtUseCase generateLftRgtUseCase;

	@Transactional
	public Category apply(CategoryInput values) {
		var file = values.getFile();
		
		var rawSlug = Utils.convertToSlug(values.getSlug());
		
		
		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Required category slug");
		}
		
		var names = values.getNames();
		
		if (names == null || names.isEmpty()) {
			throw new ApplicationException("Required category name");
		}
		
		for (var n : names) {
			if (!Utils.hasText(n.getLang())) {
				throw new ApplicationException("Required category name's lang");
			}
			
			if (!Utils.hasText(n.getName())) {
				throw new ApplicationException("Required category name");
			}
		}

		if (values.getCategoryId() != null && !dao.existsById(values.getCategoryId())) {
			throw new ApplicationException("Parent category not found");
		}
		
		var old = dao.findById(values.getId());
		
		var needGenerateLftRgt = old == null || values.getCategoryId() != Optional.ofNullable(old.getCategory()).map(Category::getId).orElse(-1);
		
		var slug = Utils.generateSlug(rawSlug, v -> dao.existsByIdNotAndSlug(values.getId(), v));
    	values.setSlug(slug);
    	
		var result = dao.save(values);

		if (file != null && !file.isEmpty()) {
			var suffix = file.getExtension();
			var dateTime = Utils.getCurrentDateTimeFormatted();
			var imageName = String.format("category-image-%d-%s.%s", result.getId(), dateTime, suffix);
			
			dao.updateImage(result.getId(), imageName);

			String dir = Constants.IMG_CATEGORY_ROOT;
			fileStorageAdapter.write(file, dir, imageName);

			var oldImage = result.getImage();

			if (Utils.hasText(oldImage)) {
				fileStorageAdapter.delete(dir, oldImage);
			}
			
			result.setImage(imageName);
		}
		
		if (needGenerateLftRgt) {
			generateLftRgtUseCase.apply();
		}
		
		return result;
		
	}

}
