package com.marketplace.data.shop;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.marketplace.data.JpaSpecificationBuilder;
import com.marketplace.data.PageDataMapper;
import com.marketplace.data.PageQueryMapper;
import com.marketplace.data.general.CityRepo;
import com.marketplace.data.shop.view.ShopCoverView;
import com.marketplace.data.shop.view.ShopExpiredAtView;
import com.marketplace.data.shop.view.ShopLogoView;
import com.marketplace.data.shop.view.ShopStatusView;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.PageData;
import com.marketplace.domain.common.PageQuery;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.Shop.Status;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.ShopCreateInput;
import com.marketplace.domain.shop.ShopUpdateInput;
import com.marketplace.domain.shop.dao.ShopDao;

@Repository
public class ShopDaoImpl implements ShopDao {

	@Autowired
	private ShopRepo shopRepo;

	@Autowired
	private ShopMemberRepo shopMemberRepo;

	@Autowired
	private ShopContactRepo shopContactRepo;
	
	@Autowired
	private CityRepo cityRepo;

	@Override
	public long create(ShopCreateInput values) {
		var entity = new ShopEntity();
		entity.setName(values.getName());
		entity.setHeadline(values.getHeadline());
		entity.setAbout(values.getAbout());

		entity.setStatus(Shop.Status.PENDING);
		entity.setSlug(values.getSlug());
		entity.setCity(cityRepo.getReferenceById(values.getCityId()));

		var result = shopRepo.save(entity);
		
		return result.getId();
	}

	@Override
	public Shop update(ShopUpdateInput values) {
		var entity = shopRepo.findById(values.getShopId())
				.orElseThrow(() -> new ApplicationException("Shop not found"));

		entity.setName(values.getName());
		entity.setHeadline(values.getHeadline());
		entity.setAbout(values.getAbout());
		entity.setSlug(values.getSlug());

		var result = shopRepo.save(entity);
		
		return ShopMapper.toDomainCompat(result);
	}

	@Override
	public void saveContact(ShopContactInput values) {
		var entity = shopContactRepo.findById(values.getShopId()).orElseGet(ShopContactEntity::new);
		entity.setAddress(values.getAddress());
		if (values.getPhones() != null) {
			entity.setPhones(values.getPhones().stream().collect(Collectors.joining(",")));
		}
		entity.setLatitude(values.getLatitude());
		entity.setLongitude(values.getLongitude());
		entity.setShop(shopRepo.getReferenceById(values.getShopId()));
		
		shopContactRepo.save(entity);
		
		shopRepo.updateCity(values.getShopId(), cityRepo.getReferenceById(values.getCityId()));
	}

	@Override
	public void updateLogo(long shopId, String logo) {
		shopRepo.updateLogo(shopId, logo);
	}

	@Override
	public void updateCover(long shopId, String cover) {
		shopRepo.updateCover(shopId, cover);
	}

	@Override
	public void updateStatus(long shopId, Shop.Status status) {
		shopRepo.updateStatus(shopId, status);
	}

	@Override
	public void updateExpiredAt(long shopId, long value) {
		shopRepo.updateExpiredAt(shopId, value);
	}
	
	@Override
	public void updateFeatured(long shopId, boolean value) {
		shopRepo.updateFeatured(shopId, value);
	}

	@Override
	public boolean existsById(long id) {
		return shopRepo.existsByIdAndDeletedFalse(id);
	}

	@Override
	public boolean existsBySlug(String slug) {
		return shopRepo.existsBySlugAndDeletedFalse(slug);
	}
	
	@Override
	public boolean existsByIdNotAndSlug(long id, String slug) {
		return shopRepo.existsByIdNotAndSlug(id, slug);
	}

	@Override
	public boolean existsByIdAndExpiredAtGreaterThanAndStatusActive(long shopId, long currentTime) {
		return shopRepo.existsByIdAndExpiredAtGreaterThanEqualAndStatus(shopId, currentTime, Shop.Status.APPROVED);
	}

	@Override
	public long count() {
		return shopRepo.countByDeletedFalse();
	}

	@Override
	public Status getStatus(long shopId) {
		return shopRepo.getShopById(shopId, ShopStatusView.class).map(ShopStatusView::getStatus).orElse(null);
	}

	@Override
	public String getLogo(long shopId) {
		return shopRepo.getShopById(shopId, ShopLogoView.class).map(ShopLogoView::getLogo).orElse(null);
	}
	
	@Override
	public long getExpiredAt(long shopId) {
		return shopRepo.getShopById(shopId, ShopExpiredAtView.class).map(ShopExpiredAtView::getExpiredAt).orElse(0L);
	}

	@Override
	public String getCover(long shopId) {
		return shopRepo.getShopById(shopId, ShopCoverView.class).map(ShopCoverView::getCover).orElse(null);
	}

	@Override
	public Shop findById(long id) {
		return shopRepo.findById(id).map(ShopMapper::toDomain).orElse(null);
	}

	@Override
	public Shop findBySlug(String slug) {
		return shopRepo.findBySlug(slug).map(e -> ShopMapper.toDomain(e)).orElse(null);
	}

	@Override
	public List<Shop> getShopHints(String q, int limit) {
		String ql = "%" + q + "%";
		return shopRepo.findShopHints(ql, ql, PageRequest.of(0, limit)).stream().map(e -> ShopMapper.toDomainCompat(e))
				.collect(Collectors.toList());
	}

	@Override
	public PageData<Shop> findByUser(long userId, PageQuery pageQuery) {
		var request = PageQueryMapper.fromPageQuery(pageQuery);
		var pageResult = shopMemberRepo.findByUserIdAndShop_DeletedFalse(userId, request);
		return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e.getShop()));
	}

	@Override
	public PageData<Shop> findAll(SearchQuery searchQuery) {
		var spec = JpaSpecificationBuilder.build(searchQuery.getCriterias(), ShopEntity.class);

        var pageable = PageQueryMapper.fromPageQuery(searchQuery.getPageQuery());

        var pageResult = spec != null ? shopRepo.findAll(spec, pageable) : shopRepo.findAll(pageable);
		return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e));
	}

}
