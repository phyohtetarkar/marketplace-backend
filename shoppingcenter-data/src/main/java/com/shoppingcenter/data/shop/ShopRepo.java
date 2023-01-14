package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.data.shop.ShopEntity.Status;

public interface ShopRepo extends JpaRepository<ShopEntity, Long>, JpaSpecificationExecutor<ShopEntity> {

	Optional<ShopEntity> findBySlug(String slug);

	List<ShopEntity> findTop8ByNameLikeOrHeadlineLike(String name, String headline);

	List<ShopEntity> findTop10ByFeaturedTrue();

	long countByStatusAndCreatedAt(Status status, long createdAt);

	boolean existsBySlug(String slug);

	@Modifying
	@Query("UPDATE Shop s SET  s.rating = :rating  WHERE s.id = :id")
	void updateRating(@Param("id") long id, @Param("rating") double rating);

}
