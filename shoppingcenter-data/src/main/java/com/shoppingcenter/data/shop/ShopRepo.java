package com.shoppingcenter.data.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepo extends JpaRepository<ShopEntity, Long>, JpaSpecificationExecutor<ShopEntity> {

	Optional<ShopEntity> findBySlug(String slug);

	List<ShopEntity> findTop8ByNameIgnoreCaseLikeOrHeadlineIgnoreCaseLike(String name, String headline);

	<T> Optional<T> getShopById(long id, Class<T> type);

	boolean existsBySlug(String slug);

	@Modifying
	@Query("UPDATE Shop s SET s.rating = :rating WHERE s.id = :id")
	void updateRating(@Param("id") long id, @Param("rating") double rating);

	@Modifying
	@Query("UPDATE Shop s SET s.featured = :featured WHERE s.id = :id")
	void updateFeatured(@Param("id") long id, @Param("featured") boolean featured);
	
	@Modifying
	@Query("UPDATE Shop s SET s.disabled = :disabled WHERE s.id = :id")
	void updateDisabled(@Param("id") long id, @Param("disabled") boolean disabled);
	
	@Modifying
	@Query("UPDATE Shop s SET s.activated = :activated WHERE s.id = :id")
	void updateActivated(@Param("id") long id, @Param("activated") boolean activated);
	
	@Modifying
	@Query("UPDATE Shop s SET s.expired = :expired WHERE s.id = :id")
	void updateExpired(@Param("id") long id, @Param("expired") boolean expired);

	@Modifying
	@Query("UPDATE Shop s SET s.logo = :logo WHERE s.id = :id")
	void updateLogo(@Param("id") long id, @Param("logo") String logo);

	@Modifying
	@Query("UPDATE Shop s SET s.cover = :cover WHERE s.id = :id")
	void updateCover(@Param("id") long id, @Param("cover") String cover);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Shop s SET s.slug = :slug WHERE s.id = :id")
	void updateSlug(@Param("id") long id, @Param("slug") String slug);

	@Query("SELECT s from Shop s WHERE (LOWER(s.name) LIKE :name or LOWER(s.headline) LIKE :headline) AND s.activated = true")
	List<ShopEntity> findShopHints(@Param("name") String name, @Param("headline") String headline, Pageable pageable);

	@Query("SELECT CASE WHEN (COUNT(s) > 0) THEN true ELSE false END FROM Shop s WHERE s.id = :id AND s.activated = true")
	boolean isShopManagable(@Param("id") long id);
}
