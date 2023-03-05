package com.shoppingcenter.search.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepo extends ElasticsearchRepository<ProductDocument, Long>, ProductSearchRepoCustom {

    Page<ProductDocument> findByShop_Id(String shopId, Pageable pageable);

    long countById(String id);

    long countByShop_Id(String shopId);

    void deleteByShop_Id(String shopId);

}
