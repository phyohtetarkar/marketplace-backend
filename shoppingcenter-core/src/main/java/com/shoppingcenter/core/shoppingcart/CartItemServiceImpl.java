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
import com.shoppingcenter.core.shoppingcart.model.CartItem.Id;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shoppingcart.CartItemEntity;
import com.shoppingcenter.data.shoppingcart.CartItemRepo;
import com.shoppingcenter.data.user.UserRepo;
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

        if (!userRepo.existsById(item.getId().getUserId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        if (!productRepo.existsById(item.getId().getProductId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        if (item.getId().getVariantId() != null && !variantRepo.existsById(item.getId().getVariantId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT);
        }

        entity.getId().setUserId(item.getId().getUserId());
        entity.getId().setProductId(item.getId().getProductId());
        entity.getId().setVariantId(item.getId().getVariantId());
        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public void updateQuantity(CartItem item) {
        CartItemEntity.Id id = new CartItemEntity.Id();
        id.setUserId(item.getId().getUserId());
        id.setProductId(item.getId().getProductId());
        id.setVariantId(item.getId().getVariantId());
        if (!cartItemRepo.existsById(id)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }

        CartItemEntity entity = cartItemRepo.getReferenceById(id);
        entity.setQuantity(item.getQuantity());
    }

    @Override
    public void removeFromCart(Id id) {
        CartItemEntity.Id itemId = new CartItemEntity.Id();
        itemId.setUserId(id.getUserId());
        itemId.setProductId(id.getProductId());
        itemId.setVariantId(id.getVariantId());
        if (!cartItemRepo.existsById(itemId)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }

        cartItemRepo.deleteById(itemId);

    }

    @Override
    public void removeByUser(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    @Override
    public void removeAll(String userId, List<Id> ids) {
        List<CartItemEntity.Id> idList = ids.stream().map(e -> {
            CartItemEntity.Id id = new CartItemEntity.Id();
            id.setUserId(userId);
            id.setProductId(e.getProductId());
            id.setVariantId(e.getVariantId());
            return id;
        }).collect(Collectors.toList());

        cartItemRepo.deleteAllByIdInBatch(idList);
    }

    @Override
    public List<CartItem> findByUser(String userId) {
        return cartItemRepo.findByUserId(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.getId().setUserId(e.getId().getUserId());
            item.getId().setProductId(e.getId().getProductId());
            item.getId().setVariantId(e.getId().getVariantId());
            item.setQuantity(e.getQuantity());
            item.setProduct(Product.createCompat(e.getProduct(), baseUrl));
            item.setVariant(ProductVariant.create(e.getVariant(), mapper));
            return item;
        }).collect(Collectors.toList());
    }

}
