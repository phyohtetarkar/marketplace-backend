package com.marketplace.data.general;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SiteSettingRepo extends JpaRepository<SiteSettingEntity, String> {

	<T> Optional<T> getSiteSettingById(String id, Class<T> type);

	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.favicon = :favicon WHERE ss.id = :id")
	void updateFavicon(@Param("id") String id, @Param("favicon") String favicon);
	
	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.logo = :logo WHERE ss.id = :id")
	void updateLogo(@Param("id") String id, @Param("logo") String logo);
	
	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.cover = :cover WHERE ss.id = :id")
	void updateCover(@Param("id") String id, @Param("cover") String cover);
	
	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.aboutUs = :value WHERE ss.id = :id")
	void updateAboutUs(@Param("id") String id, @Param("value") String value);
	
	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.termsAndConditions = :value WHERE ss.id = :id")
	void updateTermsAndConditions(@Param("id") String id, @Param("value") String value);
	
	@Modifying
	@Query("UPDATE SiteSetting ss SET ss.privacyPolicy = :value WHERE ss.id = :id")
	void updatePrivacyPolicy(@Param("id") String id, @Param("value") String value);

}
