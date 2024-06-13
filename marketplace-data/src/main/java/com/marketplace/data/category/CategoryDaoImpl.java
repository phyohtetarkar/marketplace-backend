package com.marketplace.data.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.category.view.CategoryImageView;
import com.marketplace.domain.PageData;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDao;
import com.marketplace.domain.category.CategoryInput;
import com.marketplace.domain.common.PageQuery;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private CategoryRepo repo;

	@Autowired
	private CategoryNameRepo nameRepo;

	@Override
	public Category save(CategoryInput values) {
		var entity = repo.findById(values.getId()).orElseGet(CategoryEntity::new);
		entity.setFeatured(values.isFeatured());
		entity.setSlug(values.getSlug());

		if (values.getCategoryId() != null) {
			entity.setCategory(repo.getReferenceById(values.getCategoryId()));
		}

		var result = repo.save(entity);

		if (values.getNames() != null) {
			values.getNames().forEach(n -> {
				var id = new CategoryNameEntity.ID(result.getId(), n.getLang());
				var en = nameRepo.findById(id).orElse(null);
				if (en == null) {
					en = new CategoryNameEntity();
					en.getId().setLang(n.getLang());
					en.setCategory(result);
				}
				en.setName(n.getName());
				nameRepo.save(en);
			});
		}

		return CategoryMapper.toDomain(result);
	}

	@Override
	public void saveLftRgt(List<Category> list) {
		for (var c : list) {
			var entity = repo.findById(c.getId()).orElse(null);

			if (entity == null) {
				continue;
			}

			if (entity.getLft() == c.getLft() && entity.getRgt() == c.getRgt()) {
				continue;
			}

			repo.updateLftRgt(entity.getId(), c.getLft(), c.getRgt());
		}
	}

	@Override
	public void updateImage(int id, String image) {
		repo.updateImage(id, image);
	}

	@Override
	public void delete(int id) {
		nameRepo.deleteByCategoryId(id);
		repo.deleteById(id);

	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public boolean existsByCategory(int id) {
		return repo.existsByCategory_Id(id);
	}

	@Override
	public boolean existsBySlug(String slug) {
		return repo.existsBySlug(slug);
	}

	@Override
	public boolean existsByIdNotAndSlug(int id, String slug) {
		return repo.existsByIdNotAndSlug(id, slug);
	}

	@Override
	public String getCategoryImage(int id) {
		return repo.getCategoryById(id, CategoryImageView.class).map(CategoryImageView::getImage).orElse(null);
	}

	@Override
	public Category findById(int id) {
		return repo.findById(id).map(CategoryMapper::toDomain).orElse(null);
	}

	@Override
	public Category findBySlug(String slug) {
		return repo.findBySlug(slug).map(CategoryMapper::toDomain).orElse(null);
	}

	@Override
	public List<Category> findRootCategories() {
		return repo.findByCategoryNull().stream().map(CategoryMapper::toDomainCompat).collect(Collectors.toList());
	}

	@Override
	public List<Category> findAll(boolean withNames) {
		return repo.findAll().stream().map(e -> CategoryMapper.toDomain(e, withNames)).collect(Collectors.toList());
	}

	@Override
	public List<Category> findByCategory(int categoryId) {
		return repo.findByCategoryId(categoryId).stream().map(CategoryMapper::toDomainCompat)
				.collect(Collectors.toList());
	}

	@Override
	public PageData<Category> findAll(PageQuery pageQuery) {
		var request = PageQueryMapper.fromPageQuery(pageQuery);

		Page<CategoryEntity> pageResult = repo.findAll(request);

		return PageDataMapper.map(pageResult, CategoryMapper::toDomainCompat);
	}

}
