package com.shoppingcenter.search.shop;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ShopSearchRepo extends ElasticsearchRepository<ShopDocument, Long>, ShopSearchRepoCustom {

}
