package com.marketplace.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Audit {

	private long createdAt;

    private String createdBy;

    private long modifiedAt;

    private String modifiedBy;
}
