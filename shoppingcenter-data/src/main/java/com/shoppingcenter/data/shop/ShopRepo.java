package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepo extends JpaRepository<ShopEntity, Long>, JpaSpecificationExecutor<ShopEntity> {

	Optional<ShopEntity> findBySlug(String slug);

	List<ShopEntity> findTop8ByNameIgnoreCaseLikeOrHeadlineIgnoreCaseLikeAndStatus(String name, String headline,
			String status);

	List<ShopEntity> findTop8ByFeaturedTrueAndStatus(String status);

	Page<ShopEntity> findByStatus(String status, Pageable pageable);

	<T> Optional<T> getShopById(long id, Class<T> type);

	long countByStatusAndCreatedAt(String status, long createdAt);

	boolean existsBySlug(String slug);

	boolean existsByIdAndStatus(long shopId, String status);

	@Modifying
	@Query("UPDATE Shop s SET s.rating = :rating WHERE s.id = :id")
	void updateRating(@Param("id") long id, @Param("rating") double rating);

	@Modifying
	@Query("UPDATE Shop s SET s.status = :status WHERE s.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") String status);

	@Modifying
	@Query("UPDATE Shop s SET s.featured = :featured WHERE s.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);

	@Modifying
	@Query("UPDATE Shop s SET s.logo = :logo WHERE s.id = :id")
	void updateLogo(@Param("id") long id, @Param("logo") String logo);

	@Modifying
	@Query("UPDATE Shop s SET s.cover = :cover WHERE s.id = :id")
	void updateCover(@Param("id") long id, @Param("cover") String cover);

	@Query("SELECT s from Shop s WHERE (LOWER(s.name) LIKE :name or LOWER(s.headline) LIKE :headline) AND s.status = 'ACTIVE'")
	List<ShopEntity> findShopHints(@Param("name") String name, @Param("headline") String headline, Pageable pageable);

	@Query("SELECT CASE WHEN (COUNT(s) > 0) THEN true ELSE false END FROM Shop s WHERE s.id = :id AND s.status = 'ACTIVE'")
	boolean isShopManagable(@Param("id") long id);
}
