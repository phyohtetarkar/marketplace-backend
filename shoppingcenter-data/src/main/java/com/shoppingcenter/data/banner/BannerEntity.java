package com.shoppingcenter.data.banner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = Utils.TABLE_PREFIX + "banner")
public class BannerEntity extends AuditingEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "TEXT")
	private String image;

	@Column(columnDefinition = "TEXT")
	private String link;

	private int position;

	public BannerEntity() {
	}

}
