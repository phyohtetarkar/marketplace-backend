package com.shoppingcenter.domain.category.usecase;

import java.util.function.BiConsumer;

import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.category.Category;
import com.shoppingcenter.domain.category.CategoryDao;
import com.shoppingcenter.domain.common.FileStorageAdapter;

import lombok.Setter;

@Setter
public class SaveCategoryUseCaseImpl implements SaveCategoryUseCase {

    private CategoryDao dao;

    private FileStorageAdapter fileStorageAdapter;

    @Override
    public void apply(Category category) {
        if (!Utils.hasText(category.getName())) {
            throw new ApplicationException("Required category name");
        }

        BiConsumer<String, String> pendingUpload = null;

        var oldImage = category.getImage();

        if (category.getFile() != null) {
            var timestamp = System.currentTimeMillis();
            String suffix = category.getFile().getExtension();
            String imageName = String.format("%d_%s.%s",
                    timestamp,
                    Utils.generateRandomCode(8),
                    suffix);

            category.setImage(imageName);

            pendingUpload = (old, newImage) -> {
                String dir = Constants.IMG_CATEGORY_ROOT;
                fileStorageAdapter.write(category.getFile(), dir, newImage);

                if (Utils.hasText(old)) {
                    fileStorageAdapter.delete(dir, old);
                }
            };
        }

        if (category.getCategoryId() != null && !dao.existsById(category.getCategoryId())) {
            throw new ApplicationException("Parent category not found");
        }

        dao.save(category);

        if (pendingUpload != null) {
            pendingUpload.accept(oldImage, category.getImage());
        }
    }

}
