package com.shoppingcenter.search.shop;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ShopSearchRepo extends ElasticsearchRepository<ShopDocument, String> {

    Optional<ShopDocument> findByEntityId(long entityId);

    void deleteByEntityId(long entityId);

}
