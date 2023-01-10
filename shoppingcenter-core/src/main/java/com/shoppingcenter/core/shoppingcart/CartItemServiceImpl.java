package com.shoppingcenter.core.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.core.ApplicationException;
import com.shoppingcenter.core.ErrorCodes;
import com.shoppingcenter.core.product.model.Product;
import com.shoppingcenter.core.product.model.ProductVariant;
import com.shoppingcenter.core.shoppingcart.model.CartItem;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shoppingcart.CartItemEntity;
import com.shoppingcenter.data.shoppingcart.CartItemRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.data.variant.ProductVariantEntity;
import com.shoppingcenter.data.variant.ProductVariantRepo;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductVariantRepo variantRepo;

    @Autowired
    private ObjectMapper mapper;

    @Value("${app.image.base-url}")
    private String baseUrl;

    @Override
    public void addToCart(CartItem item) {
        CartItemEntity entity = new CartItemEntity();

        if (!userRepo.existsById(item.getUserId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        if (!productRepo.existsById(item.getProductId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        ProductVariantEntity.ID variantId = new ProductVariantEntity.ID(item.getProductId(), item.getOptionPath());
        if (item.getOptionPath() != null && !variantRepo.existsById(variantId)) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        entity.getId().setUserId(item.getUserId());
        entity.getId().setProductId(item.getProductId());
        entity.getId().setOptionPath(item.getOptionPath());
        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public void updateQuantity(CartItem item) {
        CartItemEntity.ID id = new CartItemEntity.ID();
        id.setUserId(item.getUserId());
        id.setProductId(item.getProductId());
        id.setOptionPath(item.getOptionPath());
        if (!cartItemRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }

        CartItemEntity entity = cartItemRepo.getReferenceById(id);
        entity.setQuantity(item.getQuantity());
    }

    @Override
    public void removeFromCart(CartItem.ID id) {
        CartItemEntity.ID itemId = new CartItemEntity.ID();
        itemId.setUserId(id.getUserId());
        itemId.setProductId(id.getProductId());
        itemId.setOptionPath(id.getOptionPath());
        if (!cartItemRepo.existsById(itemId)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Item not found");
        }

        cartItemRepo.deleteById(itemId);

    }

    @Override
    public void removeByUser(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    @Override
    public void removeAll(String userId, List<CartItem.ID> ids) {
        List<CartItemEntity.ID> idList = ids.stream().map(e -> {
            CartItemEntity.ID id = new CartItemEntity.ID();
            id.setUserId(userId);
            id.setProductId(e.getProductId());
            id.setOptionPath(e.getOptionPath());
            return id;
        }).collect(Collectors.toList());

        cartItemRepo.deleteAllByIdInBatch(idList);
    }

    @Override
    public List<CartItem> findByUser(String userId) {
        return cartItemRepo.findByUserId(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.setUserId(e.getId().getUserId());
            item.setProductId(e.getId().getProductId());
            item.setOptionPath(e.getId().getOptionPath());
            item.setQuantity(e.getQuantity());
            item.setProduct(Product.createCompat(e.getProduct(), baseUrl));
            item.setVariant(ProductVariant.create(e.getVariant(), mapper));
            return item;
        }).collect(Collectors.toList());
    }

}
