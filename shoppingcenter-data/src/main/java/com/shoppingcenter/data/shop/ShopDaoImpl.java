package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public long create(Shop shop) {
        var entity = new ShopEntity();
        entity.setName(shop.getName());
        entity.setHeadline(shop.getHeadline());
        entity.setStatus(Shop.Status.PENDING.name());
        entity.setAbout(shop.getAbout());

        String prefix = shop.getSlug().replaceAll("\\s+", "-").toLowerCase();
        String slug = Utils.generateSlug(prefix, shopRepo::existsBySlug);
        entity.setSlug(slug);

        var result = shopRepo.save(entity);

        // eventPublisher.publishEvent(new ShopCreateEvent(this,
        // ShopMapper.toDomain(result, null)));

        return result.getId();
    }

    @Override
    public void updateGeneralInfo(ShopGeneral general) {
        var entity = shopRepo.findById(general.getShopId())
                .orElseThrow(() -> new ApplicationException("Shop not found"));

        if (!Utils.equalsIgnoreCase(entity.getName(), general.getName())) {
            String prefix = general.getSlug().replaceAll("\\s+", "-").toLowerCase();
            String slug = Utils.generateSlug(prefix, shopRepo::existsBySlug);
            entity.setSlug(slug);
        }

        entity.setName(general.getName());
        entity.setHeadline(general.getHeadline());
        entity.setAbout(general.getAbout());

        shopRepo.save(entity);
    }

    @Override
    public void saveContact(ShopContact contact) {
        var entity = shopContactRepo.findById(contact.getId()).orElseGet(ShopContactEntity::new);
        entity.setAddress(contact.getAddress());
        if (contact.getPhones() != null) {
            entity.setPhones(contact.getPhones().stream().collect(Collectors.joining(",")));
        }
        entity.setLatitude(contact.getLatitude());
        entity.setLongitude(contact.getLongitude());

        if (entity.getId() == 0) {
            entity.setShop(shopRepo.getReferenceById(contact.getShopId()));
        }

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
    public void updateStatus(long shopId, Status status) {
        shopRepo.updateStatus(shopId, imageUrl);
    }

    @Override
    public void updateRating(long shopId, double rating) {
        shopRepo.updateRating(shopId, rating);
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
    public boolean existsByIdAndStatus(long id, Status status) {
        return shopRepo.existsByIdAndStatus(id, status.name());
    }

    @Override
    public Status getStatus(long shopId) {
        return shopRepo.getShopById(shopId, ShopStatusView.class).map(v -> Shop.Status.valueOf(v.getStatus()))
                .orElse(null);
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
        return shopRepo.findById(id).map(e -> ShopMapper.toDomain(e, imageUrl)).orElse(null);
    }

    @Override
    public Shop findBySlug(String slug) {
        return shopRepo.findBySlug(slug).map(e -> ShopMapper.toDomain(e, imageUrl)).orElse(null);
    }

    @Override
    public List<Shop> getShopHints(String q, int limit) {
        String ql = "%" + q + "%";
        return shopRepo.findShopHints(ql, ql, PageRequest.of(0, limit)).stream()
                .map(e -> ShopMapper.toDomainCompat(e, imageUrl)).collect(Collectors.toList());
    }

    @Override
    public PageData<Shop> findByUser(String userId, int page) {
        var request = PageRequest.of(page, Constants.PAGE_SIZE);
        var status = Status.PENDING.name();
        var pageResult = shopMemberRepo.findByUser_IdAndShopStatusNot(userId, status, request);
        return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e.getShop(), imageUrl));
    }

    @Override
    public PageData<Shop> getShops(ShopQuery query) {
        Specification<ShopEntity> spec = null;

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ShopEntity> nameSpec = new BasicSpecification<>(new SearchCriteria("name", Operator.LIKE, q));
            Specification<ShopEntity> headlineSpec = new BasicSpecification<>(
                    new SearchCriteria("headline", Operator.LIKE, q));
            spec = Specification.where(nameSpec.or(headlineSpec));
        }

        if (query.getStatus() != null) {
            Specification<ShopEntity> statusSpec = new BasicSpecification<>(
                    new SearchCriteria("status", Operator.EQUAL, query.getStatus().name()));

            spec = spec != null ? spec.and(statusSpec) : Specification.where(statusSpec);
        }

        // Specification<ShopEntity> statusSpec = new BasicSpecification<>(
        // new SearchCriteria("status", Operator.EQUAL, "ACTIVE"));

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = shopRepo.findAll(spec, pageable);
        return PageDataMapper.map(pageResult, e -> ShopMapper.toDomainCompat(e, imageUrl));
    }

}
