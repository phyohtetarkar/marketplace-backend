package com.shoppingcenter.data.shop;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.shop.view.ShopCoverView;
import com.shoppingcenter.data.shop.view.ShopLogoView;
import com.shoppingcenter.data.shop.view.ShopStatusView;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.Utils;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopDao;

@Repository
public class ShopDaoImpl implements ShopDao {

	@Autowired
	private ShopRepo shopRepo;

	@Autowired
	private ShopMemberRepo shopMemberRepo;

	@Autowired
	private ShopContactRepo shopContactRepo;

	@Override
	public long create(ShopCreateInput data) {
		var entity = new ShopEntity();
		entity.setName(data.getName());
		entity.setHeadline(data.getHeadline());
		entity.setAbout(data.getAbout());

		entity.setStatus(Shop.Status.PENDING);
		
		if (!shopRepo.existsByIdNotAndSlug(entity.getId(), data.getSlug())) {
        	entity.setSlug(data.getSlug());
        } else {
        	var slug = Utils.generateSlug(Utils.convertToSlug(data.getName()), v -> shopRepo.existsByIdNotAndSlug(entity.getId(), v));
        	entity.setSlug(slug);
        }

		var result = shopRepo.save(entity);

//		var slug = result.getSlug() + "-" + result.getId();
//
//		shopRepo.updateSlug(result.getId(), slug);

		return result.getId();
	}

	@Override
	public void updateGeneralInfo(ShopGeneral general) {
		var entity = shopRepo.findById(general.getShopId())
				.orElseThrow(() -> new ApplicationException("Shop not found"));

		entity.setName(general.getName());
		entity.setHeadline(general.getHeadline());
		entity.setAbout(general.getAbout());
		
		if (!shopRepo.existsByIdNotAndSlug(entity.getId(), general.getSlug())) {
        	entity.setSlug(general.getSlug());
        } else {
        	var slug = Utils.generateSlug(Utils.convertToSlug(general.getName()), v -> shopRepo.existsByIdNotAndSlug(entity.getId(), v));
        	entity.setSlug(slug);
        }

		shopRepo.save(entity);

//		var slug = result.getSlug() + "-" + result.getId();
//
//		shopRepo.updateSlug(result.getId(), slug);
	}

	@Override
	public void saveContact(ShopContact contact) {
		var entity = shopContactRepo.findById(contact.getShopId()).orElseGet(ShopContactEntity::new);
		entity.setAddress(contact.getAddress());
		if (contact.getPhones() != null) {
			entity.setPhones(contact.getPhones().stream().collect(Collectors.joining(",")));
		}
		entity.setLatitude(contact.getLatitude());
		entity.setLongitude(contact.getLongitude());
		entity.setShop(shopRepo.getReferenceById(contact.getShopId()));

		shopContactRepo.save(entity);
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
	public void updateRating(long shopId, double rating) {
		var entity = shopRepo.getReferenceById(shopId);
		entity.setRating(BigDecimal.valueOf(rating));
	}

	@Override
	public void delete(long id) {
		shopRepo.deleteById(id);
	}

	@Override
	public boolean existsById(long id) {
		return shopRepo.existsById(id);
	}

	@Override
	public boolean existsBySlug(String slug) {
		return shopRepo.existsBySlug(slug);
	}
	
	@Override
	public boolean existsByIdAndExpiredAtGreaterThan(long shopId, long currentTime) {
		return shopRepo.existsByIdAndExpiredAtGreaterThanEqual(shopId, currentTime);
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
	public String getCover(long shopId) {
		return shopRepo.getShopById(shopId, ShopCoverView.class).map(ShopCoverView::getCover).orElse(null);
	}

	@Override
	public Shop findById(long id) {
		return shopRepo.findById(id).map(e -> {
			var shop = ShopMapper.toDomain(e);
			var members = e.getMembers().stream().map(ShopMemberMapper::toDomain).toList();
			shop.setMembers(members);
			return shop;
		}).orElse(null);
		
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
	public PageData<Shop> findByUser(long userId, int page) {
		var request = PageRequest.of(page, Constants.PAGE_SIZE);
		var pageResult = shopMemberRepo.findByUserId(userId, request);
		return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e.getShop()));
	}

	@Override
	public PageData<Shop> getShops(ShopQuery query) {
		Specification<ShopEntity> spec = null;

		if (StringUtils.hasText(query.getQ())) {
			String q = query.getQ().toLowerCase();
			Specification<ShopEntity> nameSpec = new BasicSpecification<>(
					new SearchCriteria("name", Operator.LIKE, "%" + q + "%"));
			Specification<ShopEntity> headlineSpec = new BasicSpecification<>(
					new SearchCriteria("headline", Operator.LIKE, "%" + q + "%"));
			spec = Specification.where(nameSpec.or(headlineSpec));
		}

		if (query.getExpired() != null) {
			var operator = query.getExpired() ? Operator.LESS_THAN : Operator.GREATER_THAN_EQ;
			Specification<ShopEntity> expiredSpec = new BasicSpecification<>(
					new SearchCriteria("expiredAt", operator, System.currentTimeMillis()));

			spec = spec != null ? spec.and(expiredSpec) : Specification.where(expiredSpec);
		}

		if (query.getStatus() != null) {
			Specification<ShopEntity> statusSpec = new BasicSpecification<>(
					new SearchCriteria("status", Operator.EQUAL, query.getStatus()));

			spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
		}

		var sort = Sort.by(Order.desc("createdAt"));

		var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

		var pageResult = shopRepo.findAll(spec, pageable);
		return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e));
	}

}
