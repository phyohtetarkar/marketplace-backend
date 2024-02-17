package com.marketplace.domain.product.usecase;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.Constants;
import com.marketplace.domain.UploadFile;
import com.marketplace.domain.Utils;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.common.FileStorageAdapter;
import com.marketplace.domain.common.HTMLStringSanitizer;
import com.marketplace.domain.discount.DiscountDao;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.dao.ProductDao;
import com.marketplace.domain.product.dao.ProductImageDao;
import com.marketplace.domain.product.dao.ProductVariantDao;
import com.marketplace.domain.shop.dao.ShopDao;

@Component
public class CreateProductUseCase {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImageDao imageDao;

	@Autowired
	private ProductVariantDao variantDao;

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private DiscountDao discountDao;

	@Autowired
	private FileStorageAdapter fileStorageAdapter;

	@Autowired
	private HTMLStringSanitizer htmlStringSanitizer;

	@Transactional
	public void apply(ProductCreateInput values) {

		if (!Utils.hasText(values.getName())) {
			throw new ApplicationException("Required product name");
		}

		var rawSlug = Utils.convertToSlug(values.getSlug());

		if (!Utils.hasText(rawSlug)) {
			throw new ApplicationException("Required product slug");
		}

		if (!categoryDao.existsById(values.getCategoryId())) {
			throw new ApplicationException("Category not found");
		}

		if (!shopDao.existsById(values.getShopId())) {
			throw new ApplicationException("Shop not found");
		}

		if (values.getDiscountId() != null && !discountDao.existsById(values.getDiscountId())) {
			throw new ApplicationException("Discount not found");
		}

		var slug = Utils.generateSlug(rawSlug, v -> productDao.existsByIdNotAndSlug(values.getId(), v));
		values.setSlug(slug);

		values.setDescription(htmlStringSanitizer.sanitize(values.getDescription()));
		values.setVideoEmbed(htmlStringSanitizer.sanitize(values.getVideoEmbed()));

		var images = Optional.ofNullable(values.getImages()).orElseGet(ArrayList::new);

		boolean atLeastOneImage = images.size() > 0;

		if (!atLeastOneImage) {
			throw new ApplicationException("At least one image required");
		}

		var variants = Optional.ofNullable(values.getVariants()).orElseGet(ArrayList::new);

		if (values.isWithVariant() && (values.getAttributes() == null || values.getAttributes().isEmpty())) {
			throw new ApplicationException("Required product attributes");
		}

		if (values.isWithVariant() && variants.isEmpty()) {
			throw new ApplicationException("Required product variants");
		}

		if (values.isWithVariant()) {
			values.setAvailable(true);
			values.setPrice(variants.stream().map(ProductCreateInput.Variant::getPrice).sorted((f, s) -> s.compareTo(f))
					.findFirst().orElse(null));
		}

		if (values.getPrice() == null) {
			throw new ApplicationException("Required product price");
		}

		var productId = productDao.create(values);

		if (values.isWithVariant()) {
			var list = values.getAttributes();
			variantDao.createProductAttributes(productId, list);
		}

		var uploadedImages = new HashMap<String, UploadFile>();

		var thumbnail = values.getThumbnail();

		var dateTime = LocalDateTime.now(ZoneOffset.UTC);
		var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

		for (var image : images) {
			if (image.getFile() == null || image.getFile().getSize() <= 0) {
				throw new ApplicationException("Uploaded image file must not empty");
			}

			var fileSize = image.getFile().getSize() / (1024.0 * 1024.0);

			if (fileSize > 0.512) {
				throw new ApplicationException("File size must not greater than 512KB");
			}

			var suffix = image.getFile().getExtension();
			var dateTimeStr = dateTime.format(dateTimeFormatter);
			var imageName = String.format("product-image-%d-%s.%s", productId, dateTimeStr, suffix);

			image.setName(imageName);
			image.setSize(image.getFile().getSize());

			uploadedImages.put(imageName, image.getFile());

			var pm = imageDao.create(productId, image);

			if (pm.isThumbnail()) {
				thumbnail = pm.getName();
			}

			dateTime = dateTime.plus(1, ChronoUnit.SECONDS);
		}

		if (thumbnail == null) {
			var imageName = uploadedImages.keySet().stream().findFirst().orElseGet(() -> {
				return values.getImages().get(0).getName();
			});
			productDao.updateThumbnail(productId, imageName);
		} else {
			productDao.updateThumbnail(productId, thumbnail);
		}

		for (var variant : variants) {
			var variantAttributes = variant.getAttributes();

			if (variantAttributes == null || variantAttributes.isEmpty()) {
				throw new ApplicationException("Invalid variant attributes");
			}

			var pv = variantDao.save(productId, variant);

			if (variant.getId() != pv.getId()) {
				for (var va : variantAttributes) {
					if (!Utils.hasText(va.getAttribute())) {
						throw new ApplicationException("Required variant attribute name");
					}

					if (!Utils.hasText(va.getValue())) {
						throw new ApplicationException("Required variant attribute value");
					}

				}

				variantDao.createVariantAttributes(productId, pv.getId(), variantAttributes);

			}
		}

		var dir = Constants.IMG_PRODUCT_ROOT;

		fileStorageAdapter.write(uploadedImages, dir);
	}

}
