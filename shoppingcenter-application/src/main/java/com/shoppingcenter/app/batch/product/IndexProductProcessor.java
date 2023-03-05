package com.shoppingcenter.app.batch.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;

import com.shoppingcenter.data.category.CategoryEntity;
import com.shoppingcenter.data.category.CategoryMapper;
import com.shoppingcenter.data.product.ProductEntity;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.search.product.CategoryDocument;
import com.shoppingcenter.search.product.ProductDocument;
import com.shoppingcenter.search.product.ProductImageDocument;

public class IndexProductProcessor implements ItemProcessor<ProductEntity, ProductDocument> {

    @Override
    public ProductDocument process(ProductEntity item) throws Exception {
        var document = new ProductDocument();
        document.setId(item.getId());
        document.setName(item.getName());
        document.setSlug(item.getSlug());
        document.setBrand(item.getBrand());
        document.setPrice(item.getPrice());
        document.setStatus(item.getStatus());
        document.setCreatedAt(item.getCreatedAt());

        document.setCategory(CategoryMapper.toDocument(item.getCategory()));
        document.setShop(ShopMapper.toDocument(item.getShop()));

        var categories = new ArrayList<CategoryDocument>();
        visitCategory(item.getCategory(), categories);

        document.setCategories(categories);

        var images = item.getImages().stream().map(value -> {
            var image = new ProductImageDocument();
            image.setId(value.getId());
            image.setName(value.getName());
            image.setThumbnail(value.isThumbnail());
            image.setSize(value.getSize());
            return image;
        }).collect(Collectors.toList());

        document.setImages(images);

        if (item.isWithVariant()) {

        }

        return document;
    }

    private void visitCategory(CategoryEntity category, List<CategoryDocument> list) {
        list.add(CategoryMapper.toDocument(category));
        if (category.getCategory() != null) {
            visitCategory(category.getCategory(), list);
        }
    }

}
