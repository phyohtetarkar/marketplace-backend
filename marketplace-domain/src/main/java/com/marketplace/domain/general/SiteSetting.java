package com.marketplace.domain.general;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteSetting {
	
	public static final String DEFAULT_ID = "site-setting";
	
	private String favicon;
	
	private String logo;
	
	private String cover;
	
	private String aboutUs;
	
	private String termsAndConditions;
	
	private String privacyPolicy;
	
	public SiteSetting() {
	}
	
}
