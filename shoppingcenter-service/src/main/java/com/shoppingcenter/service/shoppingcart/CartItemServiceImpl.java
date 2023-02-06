package com.shoppingcenter.service.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shoppingcart.CartItemEntity;
import com.shoppingcenter.data.shoppingcart.CartItemRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.data.variant.ProductVariantRepo;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.ErrorCodes;
import com.shoppingcenter.service.product.model.Product;
import com.shoppingcenter.service.product.model.ProductVariant;
import com.shoppingcenter.service.shoppingcart.model.CartItem;

@Service
@Transactional
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

        if (!userRepo.existsByIdAndDisabledFalse(item.getUserId())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "User not found");
        }

        if (!productRepo.existsByIdAndStatus(item.getProductId(), Product.Status.PUBLISHED.name())) {
            throw new ApplicationException(ErrorCodes.INVALID_ARGUMENT, "Product not found");
        }

        entity.setUser(userRepo.getReferenceById(item.getUserId()));
        entity.setProduct(productRepo.getReferenceById(item.getProductId()));

        if (item.getVariantId() != null && variantRepo.existsById(item.getVariantId())) {
            entity.setVariant(variantRepo.getReferenceById(item.getVariantId()));
        }

        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public CartItem updateQuantity(long id, int quantity, String userId) {
        if (!cartItemRepo.existsByIdAndUser_Id(id, userId)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Cart item not found");
        }
        cartItemRepo.updateQuantity(id, quantity, userId);

        return cartItemRepo.findById(id).map(e -> {
            CartItem item = new CartItem();
            item.setId(e.getId());
            item.setProductId(e.getProduct().getId());
            item.setQuantity(e.getQuantity());
            item.setProduct(Product.createCompat(e.getProduct(), baseUrl));
            if (e.getVariant() != null) {
                item.setVariant(ProductVariant.create(e.getVariant(), mapper));
                item.setVariantId(e.getVariant().getId());
            }
            return item;
        }).get();
    }

    @Override
    public void removeFromCart(String userId, long id) {
        if (!StringUtils.hasText(userId) || !cartItemRepo.existsByIdAndUser_Id(id, userId)) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND, "Cart item not found");
        }
        cartItemRepo.deleteById(id);
    }

    @Override
    public void removeByUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Failed to delete cart item");
        }
        cartItemRepo.deleteByUser_Id(userId);
    }

    @Override
    public void removeAll(String userId, List<Long> ids) {
        if (!StringUtils.hasText(userId)) {
            throw new ApplicationException(ErrorCodes.EXECUTION_FAILED, "Failed to delete cart item");
        }
        cartItemRepo.deleteByUser_IdAndIdIn(userId, ids);
    }

    @Override
    public List<CartItem> findByUser(String userId) {
        return cartItemRepo.findByUser_Id(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.setId(e.getId());
            item.setProductId(e.getProduct().getId());
            item.setQuantity(e.getQuantity());
            item.setProduct(Product.createCompat(e.getProduct(), baseUrl));
            if (e.getVariant() != null) {
                item.setVariant(ProductVariant.create(e.getVariant(), mapper));
                item.setVariantId(e.getVariant().getId());
            }
            return item;
        }).collect(Collectors.toList());
    }

}
