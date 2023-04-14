package com.shoppingcenter.data.banner;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Banner")
@Table(name = Constants.TABLE_PREFIX + "banner")
public class BannerEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// @Column(columnDefinition = "TEXT")
	private String image;

	@Column(columnDefinition = "TEXT")
	private String link;

	private int position;

	public BannerEntity() {
	}

}
