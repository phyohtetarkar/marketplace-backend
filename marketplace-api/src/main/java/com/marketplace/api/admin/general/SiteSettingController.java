package com.marketplace.api.admin.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/site-setting")
@PreAuthorize("hasPermission('SITE_SETTING', 'WRITE')")
@Tag(name = "Admin")
public class SiteSettingController {

	@Autowired
	private SiteSettingControllerFacade facade;
	
	@PutMapping("favicon")
	public void uploadFavicon(@RequestPart MultipartFile file) {
		facade.uploadFavicon(file);
	}
	
	@PutMapping("logo")
	public void uploadLogo(@RequestPart MultipartFile file) {
		facade.uploadLogo(file);
	}
	
	@PutMapping("cover")
	public void uploadCover(@RequestPart MultipartFile file) {
		facade.uploadCover(file);
	}
	
	@PutMapping("about-us")
	public void updateAboutUs(@RequestParam String value) {
		facade.saveAboutUs(value);
	}
	
	@PutMapping("terms-and-conditions")
	public void updateTermsAndConditions(@RequestParam String value) {
		facade.saveTermsAndConditions(value);
	}
	
	@PutMapping("privacy-policy")
	public void updatePrivacyPolicy(@RequestParam String value) {
		facade.savePrivacyPolicy(value);
	}
	
	@GetMapping
	public SiteSettingDTO getSiteSetting() {
		return facade.getSiteSetting();
	}
	
}
