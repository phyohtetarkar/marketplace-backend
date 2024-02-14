package com.marketplace.data;

import com.marketplace.domain.Audit;

public class AuditMapper {

	public static Audit from(AuditingEntity entity) {
		var audit = new Audit();
		audit.setCreatedAt(entity.getCreatedAt());
		audit.setCreatedBy(entity.getCreatedBy());
		audit.setModifiedAt(entity.getModifiedAt());
		audit.setModifiedBy(entity.getModifiedBy());
		return audit;
	}
	
}
