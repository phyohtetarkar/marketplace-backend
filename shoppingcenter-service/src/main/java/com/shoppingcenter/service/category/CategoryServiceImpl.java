package com.shoppingcenter.service.category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryRepo;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.category.model.Category;
import com.shoppingcenter.service.storage.FileStorageService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private FileStorageService storageService;

	@Value("${app.image.base-url}")
	private String imageUrl;

	@Value("${app.image.base-path}")
	private String imagePath;

	@Transactional
	@Override
	public void save(Category category) {
		try {
			CategoryEntity entity = categoryRepo.findById(category.getId()).orElseGet(CategoryEntity::new);

			if (entity.getName() == null || !Utils.equalIgnorecase(entity.getName(), category.getName())) {
				String prefix = category.getSlug().replaceAll("\\s+", "-").toLowerCase();
				String slug = Utils.generateSlug(prefix, categoryRepo::existsBySlug);
				entity.setSlug(slug);
			}

			entity.setName(category.getName());
			entity.setFeatured(category.isFeatured());

			if (category.getCategoryId() != null) {
				if (!categoryRepo.existsById(category.getCategoryId())) {
					throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Parent category not found");
				}
				CategoryEntity parent = categoryRepo.getReferenceById(category.getCategoryId());
				entity.setCategory(parent);
			}

			CategoryEntity result = categoryRepo.save(entity);
			result.setRootId(parseRootId(result));

			if (category.getFile() != null) {
				// long millis = System.currentTimeMillis();
				String name = String.format("category-%d.%s", result.getId(),
						category.getFile().getExtension());
				String dir = imagePath + File.separator + "category";
				// String oldImage = result.getImage();

				String image = storageService.write(category.getFile(), dir, name);
				result.setImage(image);

				// if (StringUtils.hasText(oldImage)) {
				// storageService.delete(dir, oldImage);
				// }
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, e.getMessage());
		}
	}

	@Transactional
	@Override
	public void delete(int id) {
		if (!categoryRepo.existsById(id)) {
			throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Category not found");
		}

		if (categoryRepo.existsByCategory_Id(id)) {
			throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Referenced by other categories");
		}

		if (productRepo.existsByCategory_Id(id)) {
			throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Referenced by products");
		}

		CategoryEntity entity = categoryRepo.getReferenceById(id);
		String image = entity.getImage();

		categoryRepo.deleteById(id);

		String dir = imagePath + File.separator + "category";
		storageService.delete(dir, image);

	}

	@Override
	public Category findById(int id) {
		return categoryRepo.findById(id).map(e -> Category.create(e, imageUrl)).orElse(null);
	}

	@Override
	public Category findBySlug(String slug) {
		return categoryRepo.findBySlug(slug).map(e -> Category.create(e, imageUrl))
				.orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, "Category not found"));
	}

	@Override
	public boolean existsBySlug(String slug, Integer excludeId) {
		CategoryEntity entity = categoryRepo.findBySlug(slug).orElse(null);

		int id = Optional.ofNullable(excludeId).orElse(0);

		return entity != null && entity.getSlug().equals(slug) && entity.getId() != id;
	}

	@Override
	public List<Category> findHierarchical() {
		// List<CategoryEntity> categories = categoryRepo.findByCategoryNull();

		// for (CategoryEntity entity : categories) {
		// List<CategoryEntity> childCategories = entity.getCategories();

		// for (CategoryEntity childEntity : childCategories) {
		// childEntity.getCategories();
		// }
		// }

		List<Category> categories = categoryRepo.findAll().stream()
				.map(e -> Category.createCompat(e, imageUrl))
				.collect(Collectors.toList());

		List<Category> result = new ArrayList<>();

		result.addAll(categories.stream().filter(c -> c.getCategoryId() == null).collect(Collectors.toList()));

		loadChildren(result, categories);

		return result;
	}

	@Override
	public List<Category> findMainCategories() {
		return categoryRepo.findByCategoryNull().stream().map(e -> Category.createCompat(e, imageUrl))
				.collect(Collectors.toList());
	}

	@Override
	public List<Category> findFlat() {
		Sort sort = Sort.by("rootId");
		return categoryRepo.findAll(sort).stream().map(e -> Category.createCompat(e, imageUrl))
				.collect(Collectors.toList());
	}

	@Override
	public PageData<Category> findAll(Integer page) {
		Sort sort = Sort.by(Order.desc("createdAt"));
		PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE, sort);

		Page<CategoryEntity> pageResult = categoryRepo.findAll(request);

		return PageData.build(pageResult, e -> Category.create(e, imageUrl));
	}

	private int parseRootId(CategoryEntity entity) {
		if (entity.getCategory() == null) {
			return entity.getId();
		}

		return parseRootId(entity.getCategory());
	}

	private void loadChildren(List<Category> parents, List<Category> list) {

		for (Category category : parents) {
			List<Category> children = list.stream()
					.filter(c -> Objects.equals(c.getCategoryId(), category.getId()))
					.collect(Collectors.toList());

			if (children.isEmpty()) {
				continue;
			}

			category.setChildren(children);
			loadChildren(children, list);
		}
	}

}
