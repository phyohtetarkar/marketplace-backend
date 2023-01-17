package com.shoppingcenter.core.shop;

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

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageData;
import com.shoppingcenter.core.shop.model.Shop;
import com.shoppingcenter.data.BasicSpecification;
import com.shoppingcenter.data.SearchCriteria;
import com.shoppingcenter.data.SearchCriteria.Operator;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.data.shop.ShopMemberEntity;
import com.shoppingcenter.data.shop.ShopMemberRepo;
import com.shoppingcenter.data.shop.ShopRepo;

@Service
public class ShopQueryServiceImpl implements ShopQueryService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public Shop findById(long id) {
        return shopRepo.findById(id).map(e -> Shop.create(e, baseUrl))
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
    }

    @Override
    public Shop findBySlug(String slug) {
        return shopRepo.findBySlug(slug).map(e -> Shop.create(e, baseUrl))
                .orElseThrow(() -> new ApplicationException(ErrorCodes.NOT_FOUND));
    }

    @Override
    public boolean existsBySlug(String slug, Long excludeId) {
        ShopEntity entity = shopRepo.findBySlug(slug).orElse(null);

        long id = Optional.ofNullable(excludeId).orElse(0L);

        return entity != null && entity.getSlug().equals(slug) && entity.getId() != id;
    }

    @Override
    public List<Shop> getHints(String q) {
        return shopRepo.findTop8ByNameLikeOrHeadlineLike(q, q).stream()
                .map(e -> Shop.createCompat(e, baseUrl))
                .collect(Collectors.toList());
    }

    @Override
    public PageData<Shop> findByUser(String userId, Integer page) {
        PageRequest request = PageRequest.of(page != null && page > 0 ? page : 1, Constants.PAGE_SIZE);
        Page<ShopMemberEntity> pageResult = shopMemberRepo.findByUser_Id(userId, request);
        return PageData.build(pageResult, e -> Shop.createCompat(e.getShop(), baseUrl));
    }

    @Override
    public PageData<Shop> findAll(ShopQuery query) {
        Specification<ShopEntity> spec = null;

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ShopEntity> nameSpec = new BasicSpecification<>(new SearchCriteria("name", Operator.LIKE, q));
            Specification<ShopEntity> headlineSpec = new BasicSpecification<>(
                    new SearchCriteria("headline", Operator.LIKE, q));
            spec = Specification.where(nameSpec).or(headlineSpec);
        }

        Sort sort = Sort.by(Order.desc("createdAt"));

        Pageable pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        Page<ShopEntity> pageResult = shopRepo.findAll(spec, pageable);

        return PageData.build(pageResult, e -> Shop.createCompat(e, baseUrl));
    }

}
