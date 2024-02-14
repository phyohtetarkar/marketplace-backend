package com.marketplace.data.category;

import java.util.List;

import com.marketplace.data.AuditingEntity;
import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Category")
@Table(name = Constants.TABLE_PREFIX + "category")
public class CategoryEntity extends AuditingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int lft;

	private int rgt;

	@Column(columnDefinition = "TEXT", unique = true)
	private String slug;

	private String image;

	private boolean featured;

	@ManyToOne
	private CategoryEntity category;

	@OneToMany(mappedBy = "category")
	private List<CategoryEntity> categories;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryNameEntity> names;

	public CategoryEntity() {
	}

}
