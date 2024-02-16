package com.marketplace.domain.general.dao;

import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.SiteSettingAssets;

public interface SiteSettingDao {
	
	void createEmpty(String id);
	
	void updateAboutUs(String id, String value);
	
	void updateTermsAndConditions(String id, String value);
	
	void updatePrivacyPolicy(String id, String value);
	
	void updateFavicon(String id, String value);
	
	void updateLogo(String id, String value);
	
	void updateCover(String id, String value);
	
	boolean exists(String id);
	
	SiteSettingAssets getSiteAssets(String id);
	
	String getAboutUs(String id);
	
	String getTermsAndConditions(String id);
	
	String getPrivacyPolicy(String id);

	SiteSetting getSiteSetting(String id);
	
}
