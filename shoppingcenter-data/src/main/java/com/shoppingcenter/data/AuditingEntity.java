package com.shoppingcenter.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@CreatedDate
    @Column(name = "created_at")
    private long createdAt;
	
    @CreatedBy
    @Column(name = "created_by", columnDefinition = "TEXT")
    private String createdBy;
    
    @LastModifiedDate
    @Column(name = "modified_at")
    private long modifiedAt;
    
    @LastModifiedBy
    @Column(name = "modified_by", columnDefinition = "TEXT")
    private String modifiedBy;
    
}
