package com.marketplace.data.general;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;
import com.marketplace.domain.general.SiteSetting;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SiteSetting")
@Table(name = Constants.TABLE_PREFIX + "site_setting")
public class SiteSettingEntity extends AuditingEntity {

	@Id
	private String id;

	private String favicon;

	private String logo;

	private String cover;

	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String aboutUs;

	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String termsAndConditions;

	@Lob
    @Basic(fetch = FetchType.LAZY)
	private String privacyPolicy;
	
	public SiteSettingEntity() {
		this.id = SiteSetting.DEFAULT_ID;
	}
}
