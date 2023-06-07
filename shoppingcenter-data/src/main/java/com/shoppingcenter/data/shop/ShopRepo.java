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

	List<ShopEntity> findTop8ByNameIgnoreCaseLikeOrHeadlineIgnoreCaseLike(String name, String headline);

	<T> Optional<T> getShopById(long id, Class<T> type);

	boolean existsBySlug(String slug);
	
	boolean existsByIdNotAndSlug(long id, String slug);
	
	boolean existsByIdAndExpiredAtGreaterThanEqual(long shopId, long currentTime);

	@Modifying
	@Query("UPDATE Shop s SET s.rating = :rating WHERE s.id = :id")
	void updateRating(@Param("id") long id, @Param("rating") double rating);

	@Modifying
	@Query("UPDATE Shop s SET s.featured = :featured WHERE s.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);
	
	@Modifying
	@Query("UPDATE Shop s SET s.status = :status WHERE s.id = :id")
	void updateStatus(@Param("id") long id, @Param("status") Shop.Status status);
	
	@Modifying
	@Query("UPDATE Shop s SET s.expiredAt = :expiredAt WHERE s.id = :id")
	void updateExpiredAt(@Param("id") long id, @Param("expiredAt") long expiredAt);

	@Modifying
	@Query("UPDATE Shop s SET s.logo = :logo WHERE s.id = :id")
	void updateLogo(@Param("id") long id, @Param("logo") String logo);

	@Modifying
	@Query("UPDATE Shop s SET s.cover = :cover WHERE s.id = :id")
	void updateCover(@Param("id") long id, @Param("cover") String cover);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Shop s SET s.slug = :slug WHERE s.id = :id")
	void updateSlug(@Param("id") long id, @Param("slug") String slug);

	@Query("SELECT s from Shop s WHERE (LOWER(s.name) LIKE :name or LOWER(s.headline) LIKE :headline) AND s.status = 'APPROVED'")
	List<ShopEntity> findShopHints(@Param("name") String name, @Param("headline") String headline, Pageable pageable);
}
