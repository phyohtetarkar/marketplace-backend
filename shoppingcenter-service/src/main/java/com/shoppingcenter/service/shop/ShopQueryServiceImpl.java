package com.shoppingcenter.service.shop;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.Constants;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.Utils;
import com.shoppingcenter.service.authorization.IAuthenticationFacade;
import com.shoppingcenter.service.shop.model.Shop;
import com.shoppingcenter.service.shop.model.Shop.Status;

@Service
public class ShopQueryServiceImpl implements ShopQueryService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public Shop findById(long id) {
        return shopRepo.findById(id).map(e -> Shop.create(e, imageUrl))
                .orElseThrow(() -> new ApplicationException("Shop not found"));
    }

    @Override
    public Shop findBySlug(String slug) {
        ShopEntity entity = shopRepo.findBySlug(slug)
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found"));
        String userId = authenticationFacade.getUserId();

        if (entity.getStatus() == Shop.Status.PENDING.name()) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
        } else if (entity.getStatus() != Shop.Status.ACTIVE.name()
                && (userId == null || !shopMemberRepo.existsByShop_IdAndUser_Id(entity.getId(), userId))) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Shop not found");
        }

        return Shop.create(entity, imageUrl);
    }

    @Override
    public boolean existsBySlug(String slug, Long excludeId) {
        ShopEntity entity = shopRepo.findBySlug(slug).orElse(null);

        long id = Optional.ofNullable(excludeId).orElse(0L);

        return entity != null && entity.getSlug().equals(slug) && entity.getId() != id;
    }

    @Override
    public List<Shop> getHints(String q) {
        return shopRepo.findTop8ByNameLikeOrHeadlineLikeAndStatus(q, q, Shop.Status.ACTIVE.name()).stream()
                .map(e -> Shop.createCompat(e, imageUrl))
                .collect(Collectors.toList());
    }

    @Override
    public PageData<Shop> findByUser(String userId, Integer page) {
        PageRequest request = PageRequest.of(Utils.normalizePage(page), Constants.PAGE_SIZE);
        String status = Status.PENDING.name();
        Page<ShopMemberEntity> pageResult = shopMemberRepo.findByUser_IdAndShopStatusNot(userId, status, request);
        return PageData.build(pageResult, e -> Shop.createCompat(e.getShop(), imageUrl));
    }

    @Override
    public PageData<Shop> findAll(ShopQuery query) {
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

        Sort sort = Sort.by(Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(Utils.normalizePage(query.getPage()), Constants.PAGE_SIZE, sort);

        Page<ShopEntity> pageResult = shopRepo.findAll(spec, pageable);

        return PageData.build(pageResult, e -> Shop.createCompat(e, imageUrl));
    }

}
