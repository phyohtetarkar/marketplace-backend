package com.marketplace.data.category;

import java.io.Serializable;
import java.util.Objects;

import com.marketplace.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CategoryName")
@Table(name = Constants.TABLE_PREFIX + "category_name")
public class CategoryNameEntity {
	
	@EmbeddedId
	private CategoryNameEntity.ID id;
	
	@Column(columnDefinition = "TEXT")
	private String name;
	
	@MapsId("categoryId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	public CategoryNameEntity() {
		this.id = new ID();
	}
	
	public String getLang() {
		return id.getLang();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "category_id")
		private long categoryId;

		private String lang; // en, mm

		public ID() {
		}

		public ID(long categoryId, String lang) {
			this.categoryId = categoryId;
			this.lang = lang;
		}

		@Override
		public int hashCode() {
			return Objects.hash(categoryId, lang);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			return categoryId == other.categoryId && Objects.equals(lang, other.lang);
		}

	}
}
