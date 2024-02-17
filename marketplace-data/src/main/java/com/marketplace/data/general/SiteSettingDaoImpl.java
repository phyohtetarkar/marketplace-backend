package com.marketplace.data.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketplace.data.general.view.SiteSettingAboutUsView;
import com.marketplace.data.general.view.SiteSettingAssetsView;
import com.marketplace.data.general.view.SiteSettingPrivacyPolicyView;
import com.marketplace.data.general.view.SiteSettingTermsAndConditionsView;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.general.SiteSettingAssets;
import com.marketplace.domain.general.dao.SiteSettingDao;

@Repository
public class SiteSettingDaoImpl implements SiteSettingDao {

	@Autowired
	private SiteSettingRepo repo;

	@Override
	public void createEmpty(String id) {
		var entity = new SiteSettingEntity();
		entity.setId(id);
		repo.save(entity);
	}

	@Override
	public void updateAboutUs(String id, String value) {
		repo.updateAboutUs(id, value);
	}

	@Override
	public void updateTermsAndConditions(String id, String value) {
		repo.updateTermsAndConditions(id, value);
	}

	@Override
	public void updatePrivacyPolicy(String id, String value) {
		repo.updatePrivacyPolicy(id, value);
	}

	@Override
	public void updateFavicon(String id, String value) {
		repo.updateFavicon(id, value);
	}

	@Override
	public void updateLogo(String id, String value) {
		repo.updateLogo(id, value);
	}

	@Override
	public void updateCover(String id, String value) {
		repo.updateCover(id, value);
	}
	
	@Override
	public boolean exists(String id) {
		return repo.existsById(id);
	}

	@Override
	public SiteSettingAssets getSiteAssets(String id) {
		return repo.getSiteSettingById(id, SiteSettingAssetsView.class).map(e -> {
			var d = new SiteSettingAssets();
			d.setCover(e.getCover());
			d.setFavicon(e.getFavicon());
			d.setLogo(e.getLogo());
			return d;
		}).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public String getAboutUs(String id) {
		return repo.getSiteSettingById(id, SiteSettingAboutUsView.class)
				.map(SiteSettingAboutUsView::getAboutUs)
				.orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public String getTermsAndConditions(String id) {
		return repo.getSiteSettingById(id, SiteSettingTermsAndConditionsView.class)
				.map(SiteSettingTermsAndConditionsView::getTermsAndConditions)
				.orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public String getPrivacyPolicy(String id) {
		return repo.getSiteSettingById(id, SiteSettingPrivacyPolicyView.class)
				.map(SiteSettingPrivacyPolicyView::getPrivacyPolicy)
				.orElse(null);
	}

	@Override
	public SiteSetting getSiteSetting(String id) {
		return repo.findById(id).map(SiteSettingMapper::toDomain).orElse(null);
	}

}
