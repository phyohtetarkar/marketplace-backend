package com.marketplace.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditDTO {
	private long createdAt;

    private String createdBy;

    private long modifiedAt;

    private String modifiedBy;
}
