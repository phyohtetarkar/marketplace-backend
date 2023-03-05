package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.search.shop.ShopDocument;
import com.shoppingcenter.search.shop.ShopSearchRepo;

@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {

    @Autowired
    private ShopSearchRepo shopSearchRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public long save(Shop shop) {
        var document = shopSearchRepo.findById(shop.getId()).orElseGet(ShopDocument::new);
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        document.setCreatedAt(shop.getCreatedAt());
        document.setStatus(shop.getStatus().name());
        document.setLogo(shop.getLogo());

        var result = shopSearchRepo.save(document);
        return result.getId();
    }

    @Override
    public void delete(long shopId) {
        shopSearchRepo.deleteById(shopId);
    }

    @Override
    public List<Shop> getHints(String q, int limit) {
        var criteria = new Criteria("status").is(Shop.Status.ACTIVE.name())
                .and("name").matchesAll(q);

        var pageable = PageRequest.of(0, limit);

        var pageResult = shopSearchRepo.findAll(criteria, pageable);

        return pageResult.get().map(v -> ShopMapper.toDomainCompat(v.getContent(), imageUrl)).toList();
    }

    @Override
    public PageData<Shop> getShops(ShopQuery query) {
        var criteria = new Criteria();

        if (query.getStatus() != null) {
            criteria = criteria.and("status").is(query.getStatus());
        } else if (query.getStatusNot() != null) {
            criteria = criteria.and("status").is(query.getStatusNot()).not();
        }

        if (StringUtils.hasText(query.getQ())) {
            var q = query.getQ().toLowerCase();
            criteria = criteria.and("name").matchesAll(q);
        }

        var sort = Sort.by(Order.desc("createdAt"));

        var pageable = PageRequest.of(query.getPage(), Constants.PAGE_SIZE, sort);

        var pageResult = shopSearchRepo.findAll(criteria, pageable);

        return PageDataMapper.map(pageResult, doc -> ShopMapper.toDomainCompat(doc.getContent(), imageUrl));
    }

}
