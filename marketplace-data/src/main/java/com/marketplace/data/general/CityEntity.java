package com.marketplace.data.general;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

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
@Entity(name = "City")
@Table(name = Constants.TABLE_PREFIX + "city")
public class CityEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String name;
	
}
