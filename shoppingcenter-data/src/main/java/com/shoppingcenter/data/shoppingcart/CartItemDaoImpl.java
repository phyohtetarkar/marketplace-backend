package com.shoppingcenter.data.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductMapper;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.shoppingcart.CartItem;
import com.shoppingcenter.domain.shoppingcart.CartItemDao;

@Repository
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private CartItemRepo cartItemRepo;

    // @Autowired
    // private ProductRepo productRepo;

    // @Autowired
    // private ProductVariantRepo variantRepo;

    // @Autowired
    // private UserRepo userRepo;

    @Autowired
    private AppProperties properties;

    @Override
    public void save(CartItem item) {
        var id = new CartItemEntity.ID(item.getUserId(), item.getProductId(), item.getVariantId());
        var entity = cartItemRepo.findById(id).orElseGet(CartItemEntity::new);
        entity.setId(id);

        // entity.setUser(userRepo.getReferenceById(id.getUserId()));
        // entity.setProduct(productRepo.getReferenceById(id.getProductId()));

        // if (id.getVariantId() > 0 && variantRepo.existsById(id.getVariantId())) {
        // entity.setVariant(variantRepo.getReferenceById(id.getVariantId()));
        // }

        entity.setQuantity(item.getQuantity());
        cartItemRepo.save(entity);
    }

    // @Override
    // public void updateQuantity(CartItem.ID itemId, int quantity) {
    // var id = new CartItemEntity.ID(itemId.getUserId(), itemId.getProductId(),
    // itemId.getVariantId());
    // cartItemRepo.updateQuantity(id, quantity);
    // }

    @Override
    public void delete(CartItem item) {
        var id = new CartItemEntity.ID(item.getUserId(), item.getProductId(), item.getVariantId());
        cartItemRepo.deleteById(id);
    }

    @Override
    public void deleteByUser(long userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    @Override
    public void deleteAll(List<CartItem> items) {
        cartItemRepo.deleteAllById(items.stream().map(item -> {
            return new CartItemEntity.ID(item.getUserId(), item.getProductId(), item.getVariantId());
        }).toList());
    }

    @Override
    public void deleteByProduct(long productId) {
        cartItemRepo.deleteByProductId(productId);
    }

    @Override
    public boolean exists(long userId, long productId, long variantId) {
        var id = new CartItemEntity.ID(userId, productId, variantId);
        return cartItemRepo.existsById(id);
    }

    @Override
    public long countByUser(long userId) {
        return cartItemRepo.countByUserId(userId);
    }

    @Override
    public List<CartItem> findByUser(long userId) {
        return cartItemRepo.findByUserId(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.setUserId(e.getId().getUserId());
            item.setProductId(e.getId().getProductId());
            item.setVariantId(e.getId().getVariantId());
            item.setQuantity(e.getQuantity());
            item.setProduct(ProductMapper.toDomainCompat(e.getProduct(), properties.getImageUrl()));
            if (e.getVariant() != null) {
                item.setVariant(ProductMapper.toVariant(e.getVariant()));
            }
            return item;
        }).collect(Collectors.toList());
    }

}
