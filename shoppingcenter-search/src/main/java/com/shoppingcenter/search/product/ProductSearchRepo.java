package com.shoppingcenter.search.product;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepo extends ElasticsearchRepository<ProductDocument, String>, ProductSearchRepoCustom {

    Optional<ProductDocument> findByEntityId(long entityId);

    Page<ProductDocument> findByShop_Id(String shopId, Pageable pageable);

    Page<ProductDocument> findByShop_EntityId(long shopId, Pageable pageable);

    long countById(String id);

    long countByShop_Id(String shopId);

    void deleteByEntityId(long productId);

    void deleteByShop_Id(String shopId);

}
