package com.shoppingcenter.core.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.Constants;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.PageResult;
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
    public PageResult<Shop> findByUser(String userId, int page) {
        Pageable pageable = PageRequest.of(page, Constants.PAGE_SIZE);
        Page<ShopMemberEntity> pageResult = shopMemberRepo.findByUserId(userId, pageable);

        PageResult<Shop> result = new PageResult<>();
        result.setContents(pageResult.map(e -> Shop.createCompat(e.getShop(), baseUrl)).toList());
        result.setCurrentPage(pageResult.getNumber());
        result.setTotalPage(pageResult.getTotalPages());
        result.setPageSize(pageResult.getNumberOfElements());
        return result;
    }

    @Override
    public PageResult<Shop> findAll(ShopQuery query) {
        Specification<ShopEntity> spec = null;

        if (StringUtils.hasText(query.getQ())) {
            String q = query.getQ().toLowerCase();
            Specification<ShopEntity> nameSpec = new BasicSpecification<>(new SearchCriteria("name", Operator.LIKE, q));
            Specification<ShopEntity> headlineSpec = new BasicSpecification<>(
                    new SearchCriteria("headline", Operator.LIKE, q));
            spec = Specification.where(nameSpec).or(headlineSpec);
        }

        Pageable pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE);

        Page<ShopEntity> pageResult = shopRepo.findAll(spec, pageable);

        PageResult<Shop> result = new PageResult<>();
        result.setContents(pageResult.map(e -> Shop.createCompat(e, baseUrl)).toList());
        result.setCurrentPage(pageResult.getNumber());
        result.setTotalPage(pageResult.getTotalPages());
        result.setPageSize(pageResult.getNumberOfElements());
        return result;
    }
}
