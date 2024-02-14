package com.marketplace.api.consumer.general;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.SiteAssetsImageSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteAssetsDTO {
	
	@JsonSerialize(using = SiteAssetsImageSerializer.class)
	private String favicon;

	@JsonSerialize(using = SiteAssetsImageSerializer.class)
	private String logo;

	@JsonSerialize(using = SiteAssetsImageSerializer.class)
	private String cover;
	
}
