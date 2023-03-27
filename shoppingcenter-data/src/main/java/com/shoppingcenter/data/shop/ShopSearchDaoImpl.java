package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.shoppingcenter.data.PageDataMapper;
import com.shoppingcenter.domain.Constants;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.dao.ShopSearchDao;
import com.shoppingcenter.search.shop.ShopDocument;
import com.shoppingcenter.search.shop.ShopSearchRepo;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;

@Repository
public class ShopSearchDaoImpl implements ShopSearchDao {

    @Autowired
    private ShopSearchRepo shopSearchRepo;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Autowired
    private AppProperties properties;

    @Override
    public long save(Shop shop) {
        var document = shopSearchRepo.findById(shop.getId()).orElseGet(ShopDocument::new);
        document.setId(shop.getId());
        document.setName(shop.getName());
        document.setSlug(shop.getSlug());
        document.setHeadline(shop.getHeadline());
        document.setCreatedAt(shop.getCreatedAt());
        document.setStatus(shop.getStatus().name());

        var result = shopSearchRepo.save(document);
        return result.getId();
    }

    @Override
    public void delete(long shopId) {
        shopSearchRepo.deleteById(shopId);
    }

    @Override
    public List<String> getSuggestions(String q, int limit) {
        return shopSearchRepo.findSuggestions(q, limit);
    }

    @Override
    public List<Shop> getHints(String q, int limit) {
        // var criteria = new Criteria("status").is(Shop.Status.ACTIVE.name())
        // .and("name").matchesAll(q);

        var pageable = PageRequest.of(0, limit);

        var query = NativeQuery.builder()
                .withQuery(qe -> {
                    var boolQuery = new BoolQuery.Builder()
                            .must(mq -> mq.match(m -> m.field("status").query(Shop.Status.ACTIVE.name())))
                            .must(mq -> mq.multiMatch(mm -> mm.fields("name", "headline").query(q)))
                            .build();
                    return qe.bool(boolQuery);
                })
                .withPageable(pageable)
                .build();

        var list = shopSearchRepo.findAll(query);

        return list.stream().map(v -> ShopMapper.toDomainCompat(v, imageUrl)).toList();
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

        var pageResult = shopSearchRepo.findAll(new CriteriaQuery(criteria, pageable), pageable);

        return PageDataMapper.map(pageResult, doc -> ShopMapper.toDomainCompat(doc.getContent(), imageUrl));
    }

}
