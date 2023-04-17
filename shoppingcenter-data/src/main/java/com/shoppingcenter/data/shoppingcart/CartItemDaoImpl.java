package com.shoppingcenter.data.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductMapper;
import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.product.variant.ProductVariantRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.common.AppProperties;
import com.shoppingcenter.domain.shoppingcart.AddToCartInput;
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
    private AppProperties properties;
    
    @Override
    public void create(AddToCartInput data) {
    	var entity = new CartItemEntity();

        entity.setUser(userRepo.getReferenceById(data.getUserId()));
        entity.setProduct(productRepo.getReferenceById(data.getProductId()));

        if (data.getVariantId() != null && variantRepo.existsById(data.getVariantId())) {
            entity.setVariant(variantRepo.getReferenceById(data.getVariantId()));
        }

        entity.setQuantity(data.getQuantity());
        cartItemRepo.save(entity);
    	
    }

    // @Override
    // public void updateQuantity(CartItem.ID itemId, int quantity) {
    // var id = new CartItemEntity.ID(itemId.getUserId(), itemId.getProductId(),
    // itemId.getVariantId());
    // cartItemRepo.updateQuantity(id, quantity);
    // }

    @Override
    public void update(long id, int quantity) {
    	var entity = cartItemRepo.getReferenceById(id);
        entity.setQuantity(quantity);
    }

    @Override
    public void delete(long id) {
        cartItemRepo.deleteById(id);
    }

    @Override
    public void deleteByUser(long userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    @Override
    public void deleteAll(List<Long> items) {
        cartItemRepo.deleteAllById(items);
    }

    @Override
    public void deleteByProduct(long productId) {
        cartItemRepo.deleteByProductId(productId);
    }

    @Override
    public boolean existsById(long id) {
        return cartItemRepo.existsById(id);
    }

    @Override
    public boolean exists(long userId, long productId, Long variantId) {
        if (variantId != null && variantId > 0) {
            return cartItemRepo.existsByUserIdAndProductIdAndVariantId(userId, productId, variantId);
        }

        return cartItemRepo.existsByUserIdAndProductId(userId, productId);
    }

    @Override
    public long countByUser(long userId) {
        return cartItemRepo.countByUserId(userId);
    }

    @Override
    public List<CartItem> findByUser(long userId) {
        return cartItemRepo.findByUserId(userId).stream().map(e -> {
            CartItem item = new CartItem();
            item.setId(e.getId());
            item.setQuantity(e.getQuantity());
            item.setProduct(ProductMapper.toDomainCompat(e.getProduct(), properties.getImageUrl()));
            if (e.getVariant() != null) {
                item.setVariant(ProductMapper.toVariant(e.getVariant()));
            }
            return item;
        }).collect(Collectors.toList());
    }

}
