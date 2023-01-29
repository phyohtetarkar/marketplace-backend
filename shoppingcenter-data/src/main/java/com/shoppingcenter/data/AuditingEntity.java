package com.shoppingcenter.data;

import java.io.Serializable;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedDate
    private long createdAt;

    @CreatedBy
    @Column(columnDefinition = "TEXT")
    private String createdBy;

    @LastModifiedDate
    private long modifiedAt;

    @LastModifiedBy
    @Column(columnDefinition = "TEXT")
    private String modifiedBy;

}
