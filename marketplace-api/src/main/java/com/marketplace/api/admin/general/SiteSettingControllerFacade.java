package com.marketplace.api.admin.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.MultipartFileConverter;
import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.domain.general.usecase.GetSiteSettingUseCase;
import com.marketplace.domain.general.usecase.SaveSiteAboutUsUseCase;
import com.marketplace.domain.general.usecase.SaveSitePrivacyPolicyUseCase;
import com.marketplace.domain.general.usecase.SaveSiteTermsAndConditionsUseCase;
import com.marketplace.domain.general.usecase.UploadSiteCoverUseCase;
import com.marketplace.domain.general.usecase.UploadSiteFaviconUseCase;
import com.marketplace.domain.general.usecase.UploadSiteLogoUseCase;

@Component
public class SiteSettingControllerFacade {

	@Autowired
	private GetSiteSettingUseCase getSiteSettingUseCase;
	
	@Autowired
	private UploadSiteFaviconUseCase uploadSiteFaviconUseCase;
	
	@Autowired
	private UploadSiteLogoUseCase uploadSiteLogoUseCase;
	
	@Autowired
	private UploadSiteCoverUseCase uploadSiteCoverUseCase;
	
	@Autowired
	private SaveSiteAboutUsUseCase saveSiteAboutUsUseCase;
	
	@Autowired
	private SaveSiteTermsAndConditionsUseCase saveSiteTermsAndConditionsUseCase;
	
	@Autowired
	private SaveSitePrivacyPolicyUseCase saveSitePrivacyPolicyUseCase;
	
	@Autowired
    private AdminDataMapper mapper;
	
	public void uploadFavicon(MultipartFile file) {
		uploadSiteFaviconUseCase.apply(MultipartFileConverter.toUploadFile(file));
	}
	
	public void uploadLogo(MultipartFile file) {
		uploadSiteLogoUseCase.apply(MultipartFileConverter.toUploadFile(file));
	}
	
	public void uploadCover(MultipartFile file) {
		uploadSiteCoverUseCase.apply(MultipartFileConverter.toUploadFile(file));
	}
	
	public void saveAboutUs(String value) {
		saveSiteAboutUsUseCase.apply(value);
	}
	
	public void saveTermsAndConditions(String value) {
		saveSiteTermsAndConditionsUseCase.apply(value);
	}
	
	public void savePrivacyPolicy(String value) {
		saveSitePrivacyPolicyUseCase.apply(value);
	}
	
	public SiteSettingDTO getSiteSetting() {
		var source = getSiteSettingUseCase.apply();
		
		return mapper.map(source);
	}
	
}
