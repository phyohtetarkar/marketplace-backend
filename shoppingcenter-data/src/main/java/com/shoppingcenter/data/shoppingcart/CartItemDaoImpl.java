package com.shoppingcenter.data.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcenter.data.product.ProductMapper;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.data.variant.ProductVariantRepo;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

@Repository
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductVariantRepo variantRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${app.image.base-url}")
    private String imageUrl;

    @Override
    public void save(CartItem item) {
        var entity = cartItemRepo.findById(item.getId()).orElseGet(CartItemEntity::new);

        entity.setUser(userRepo.getReferenceById(item.getUserId()));
        entity.setProduct(productRepo.getReferenceById(item.getProductId()));

        if (item.getVariantId() != null && variantRepo.existsById(item.getVariantId())) {
            entity.setVariant(variantRepo.getReferenceById(item.getVariantId()));
        }

        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public void updateQuantity(long itemId, int quantity) {
        cartItemRepo.updateQuantity(itemId, quantity);
    }

    @Override
    public void delete(long itemId) {
        cartItemRepo.deleteById(itemId);
    }

    @Override
    public void deleteByUser(String userId) {
        cartItemRepo.deleteByUser_Id(userId);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        cartItemRepo.deleteAllById(ids);
    }

    @Override
    public void deleteByProduct(long productId) {
        cartItemRepo.deleteByProduct_Id(productId);
    }

    @Override
    public boolean existsByIdAndUser(long itemId, String userId) {
        return cartItemRepo.existsByIdAndUser_Id(itemId, userId);
    }

    @Override
    public boolean existsByUserAndProductAndVariant(String userId, long productId, Long variantId) {
        if (variantId == null) {
            return cartItemRepo.existsByUser_IdAndProduct_Id(userId, productId);
        }
        return cartItemRepo.existsByUser_IdAndProduct_IdAndVariant_Id(userId, productId, variantId);
    }

    @Override
    public long countByUser(String userId) {
        return cartItemRepo.countByUser_Id(userId);
    }

    @Override
    public List<CartItem> findByUser(String userId) {
        return cartItemRepo.findByUser_Id(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.setId(e.getId());
            item.setProductId(e.getProduct().getId());
            item.setQuantity(e.getQuantity());
            item.setProduct(ProductMapper.toDomainComapt(e.getProduct(), imageUrl));
            if (e.getVariant() != null) {
                item.setVariant(ProductMapper.toVariant(e.getVariant(), objectMapper));
                item.setVariantId(e.getVariant().getId());
            }
            return item;
        }).collect(Collectors.toList());
    }

}
