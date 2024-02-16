package com.marketplace.api.vendor.product;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImagesUpdateDTO {

	private List<ProductCreateDTO.Image> images;
	
}
