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
        if (!userRepo.existsByIdAndDisabledFalse(item.getUserId())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "User not found");
        }

        if (!productRepo.existsByIdAndStatus(item.getProductId(), Product.Status.PUBLISHED.name())) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Product not found");
        }

        boolean withVariant = item.getVariantId() != null && variantRepo.existsById(item.getVariantId());

        if (withVariant && cartItemRepo.existsByUser_IdAndProduct_IdAndVariant_Id(item.getUserId(), item.getProductId(),
                item.getVariantId())) {
            return;
        }

        if (!withVariant && cartItemRepo.existsByUser_IdAndProduct_Id(item.getUserId(), item.getProductId())) {
            return;
        }

        CartItemEntity entity = new CartItemEntity();

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
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Cart item not found");
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
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Cart item not found");
        }
        cartItemRepo.deleteById(id);
    }

    @Override
    public void removeByUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Failed to delete cart item");
        }
        cartItemRepo.deleteByUser_Id(userId);
    }

    @Override
    public void removeAll(String userId, List<Long> ids) {
        if (!StringUtils.hasText(userId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Failed to delete cart item");
        }
        cartItemRepo.deleteByUser_IdAndIdIn(userId, ids);
    }

    @Override
    public long countByUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new ApplicationException(ErrorCodes.VALIDATION_FAILED, "Unknown user");
        }
        return cartItemRepo.countByUser_Id(userId);
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
