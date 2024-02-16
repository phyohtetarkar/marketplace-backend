package com.marketplace.data.general;

import com.marketplace.domain.general.SiteSetting;

public interface SiteSettingMapper {

	static SiteSetting toDomain(SiteSettingEntity entity) {
		var ss = new SiteSetting();
		ss.setFavicon(entity.getFavicon());
		ss.setLogo(entity.getLogo());
		ss.setCover(entity.getCover());
		ss.setAboutUs(entity.getAboutUs());
		ss.setTermsAndConditions(entity.getTermsAndConditions());
		ss.setPrivacyPolicy(entity.getPrivacyPolicy());
		return ss;
	}
	
}
