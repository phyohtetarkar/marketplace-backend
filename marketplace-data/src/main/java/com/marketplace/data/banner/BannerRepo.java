package com.marketplace.data.banner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BannerRepo extends JpaRepository<BannerEntity, Integer> {

    <T> Optional<T> getBannerById(int id, Class<T> type);
    
    @Modifying
	@Query("UPDATE Banner b SET b.image = :image WHERE b.id = :id")
	void updateImage(@Param("id") int id, @Param("image") String image);

}
