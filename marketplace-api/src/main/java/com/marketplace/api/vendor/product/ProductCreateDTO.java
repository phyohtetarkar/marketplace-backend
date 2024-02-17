package com.marketplace.api.vendor.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {

    private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private boolean available;

    private boolean newArrival;

    private boolean withVariant;
    
    private boolean draft;

    private String description;
    
    private String videoEmbed;

    private List<Attribute> attributes;

    private List<Variant> variants;

    private List<Image> images;

    private Long discountId;

    private int categoryId;

    @JsonIgnore
    private long shopId;
    
    @Getter
	@Setter
	public static class Image {
		private long id;

		private boolean thumbnail;
		
		private long size;

		private MultipartFile file;

		private boolean deleted;
	}

	@Getter
	@Setter
	public static class Attribute {

		private String name;

		private int sort;

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Attribute other = (Attribute) obj;
			return Objects.equals(name, other.name);
		}

	}

	@Getter
	@Setter
	public static class Variant {
		private long id;
		
		private String sku;

		private BigDecimal price;

		private boolean available;

		private List<VariantAttribute> attributes;

		private boolean deleted;
	}

	@Getter
	@Setter
	public static class VariantAttribute {

		private String attribute;

		private String value;

		private int sort;

		@JsonProperty("vSort")
		private int vSort;
	}
}
