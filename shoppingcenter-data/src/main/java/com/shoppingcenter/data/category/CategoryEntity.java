package com.shoppingcenter.data.category;

import java.util.List;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.domain.Constants;

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

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int lft;

	private int rgt;

	@Column(columnDefinition = "TEXT")
	private String name;

	@Column(columnDefinition = "TEXT")
	private String slug;

	// @Column(columnDefinition = "TEXT")
	private String image;

	private boolean featured;

	@ManyToOne
	private CategoryEntity category;

	@OneToMany(mappedBy = "category")
	private List<CategoryEntity> categories;

	public CategoryEntity() {
	}

}
