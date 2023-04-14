package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shoppingcenter.domain.shop.Shop;

public interface ShopRepo extends JpaRepository<ShopEntity, Long>, JpaSpecificationExecutor<ShopEntity> {

	Optional<ShopEntity> findBySlug(String slug);

	List<ShopEntity> findTop8ByNameIgnoreCaseLikeOrHeadlineIgnoreCaseLikeAndStatus(String name, String headline,
			Shop.Status status);

	<T> Optional<T> getShopById(long id, Class<T> type);

	long countByStatusAndCreatedAt(Shop.Status status, long createdAt);

	boolean existsBySlug(String slug);

	void deleteByStatus(Shop.Status status);

	@Modifying
	@Query("UPDATE Shop s SET s.rating = :rating WHERE s.id = :id")
	void updateRating(@Param("id") long id, @Param("rating") double rating);

	@Modifying
	@Query("UPDATE Shop s SET s.totalProduct = :totalProduct WHERE s.id = :id")
	void updateTotalProduct(@Param("id") long id, @Param("totalProduct") int totalProduct);

	@Modifying
	@Query("UPDATE Shop s SET s.totalSale = :totalSale WHERE s.id = :id")
	void updateTotalSale(@Param("id") long id, @Param("totalSale") long totalSale);

	@Modifying
	@Query("UPDATE Shop s SET s.pendingOrderCount = :pendingOrderCount WHERE s.id = :id")
	void updatePendingOrder(@Param("id") long id, @Param("pendingOrderCount") int pendingOrderCount);

	@Modifying
	@Query("UPDATE Shop s SET s.totalOrderCount = :totalOrderCount WHERE s.id = :id")
	void updateTotalOrder(@Param("id") long id, @Param("totalOrderCount") long totalOrderCount);

	@Modifying
	@Query("UPDATE Shop s SET s.status = :status WHERE s.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") Shop.Status status);

	@Modifying
	@Query("UPDATE Shop s SET s.featured = :featured WHERE s.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);

	@Modifying
	@Query("UPDATE Shop s SET s.logo = :logo WHERE s.id = :id")
	void updateLogo(@Param("id") long id, @Param("logo") String logo);

	@Modifying
	@Query("UPDATE Shop s SET s.cover = :cover WHERE s.id = :id")
	void updateCover(@Param("id") long id, @Param("cover") String cover);

	// @Modifying
	// @Query("UPDATE Shop s SET s.disabled = :disabled WHERE s.id = :id")
	// void toggleDisabled(@Param("id") long id, @Param("disabled") boolean
	// disabled);

	// @Modifying
	// @Query("UPDATE Shop s SET s.expired = :expired WHERE s.id = :id")
	// void toggleExpired(@Param("id") long id, @Param("expired") boolean expired);

	@Query("SELECT s from Shop s WHERE (LOWER(s.name) LIKE :name or LOWER(s.headline) LIKE :headline) AND s.status = 'ACTIVE'")
	List<ShopEntity> findShopHints(@Param("name") String name, @Param("headline") String headline, Pageable pageable);

	@Query("SELECT CASE WHEN (COUNT(s) > 0) THEN true ELSE false END FROM Shop s WHERE s.id = :id AND s.status = 'ACTIVE'")
	boolean isShopManagable(@Param("id") long id);
}
