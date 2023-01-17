package com.shoppingcenter.core.shoppingcart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

        entity.setUser(userRepo.getReferenceById(item.getUserId()));
        entity.setProduct(productRepo.getReferenceById(item.getProductId()));

        if (item.getVariantId() != null && variantRepo.existsById(item.getVariantId())) {
            entity.setVariant(variantRepo.getReferenceById(item.getVariantId()));
        }

        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public void updateQuantity(CartItem item) {
        if (!StringUtils.hasText(item.getId()) || !cartItemRepo.existsById(item.getId())) {
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }

        CartItemEntity entity = cartItemRepo.getReferenceById(item.getId());
        entity.setQuantity(item.getQuantity());
    }

    @Override
    public void removeFromCart(String id) {
        String itemId = Optional.ofNullable(id).orElse("");

        cartItemRepo.deleteById(itemId);

    }

    @Override
    public void removeByUser(String userId) {
        cartItemRepo.deleteByUser_Id(userId);
    }

    @Override
    public void removeAll(List<String> ids) {
        cartItemRepo.deleteAllByIdInBatch(ids);
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
