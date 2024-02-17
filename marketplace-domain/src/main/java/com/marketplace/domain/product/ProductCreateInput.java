package com.marketplace.domain.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.marketplace.domain.UploadFile;
import com.marketplace.domain.product.Product.Status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateInput {

	private long id;

    private String sku;

    private String name;

    private String slug;

    private String brand;

    private BigDecimal price;

    private boolean available;

    private String thumbnail;

    private boolean newArrival;

    private boolean withVariant;

    private String description;
    
    private String videoEmbed;

    @Setter(value = AccessLevel.NONE)
    private Product.Status status;

    private List<Image> images;

    private List<Attribute> attributes;

    private List<Variant> variants;
    
    private boolean draft;

    private Long discountId;

    private int categoryId;

    private long shopId;
    
    public ProductCreateInput() {
    	this.status = Status.DRAFT;
	}
    
    public Product.Status getStatus() {
    	if (draft) {
    		return Product.Status.DRAFT;
    	}
    	return Product.Status.PUBLISHED;
    }
    
    @Getter
	@Setter
	public static class Image {
		private long id;

		private String name;

		private boolean thumbnail;
		
		private long size;

		private UploadFile file;

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

		private int vSort;
	}
}
