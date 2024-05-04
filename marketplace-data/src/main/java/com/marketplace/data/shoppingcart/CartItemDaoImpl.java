package com.marketplace.data.shoppingcart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.product.ProductRepo;
import com.marketplace.data.product.ProductVariantRepo;
import com.marketplace.data.user.UserRepo;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemDao;
import com.marketplace.domain.shoppingcart.CartItemInput;

@Repository
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private CartItemRepo cartItemRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    private ProductVariantRepo productVariantRepo;
    
    @Override
    public void create(CartItemInput values) {
    	var entity = new CartItemEntity();
//    	var id = new CartItemEntity.ID(values.getUserId(), values.getProductId(), values.getVariantId());
        entity.setUser(userRepo.getReferenceById(values.getUserId()));
        entity.setProduct(productRepo.getReferenceById(values.getProductId()));

        if (values.getVariantId() > 0 && productVariantRepo.existsById(values.getVariantId())) {
        	entity.getId().setVariantId(values.getVariantId());
            entity.setVariant(productVariantRepo.getReferenceById(values.getVariantId()));
        }
        
        entity.setQuantity(values.getQuantity());
        cartItemRepo.save(entity);
    	
    }

    @Override
    public void update(CartItemInput values) {
    	var id = new CartItemEntity.ID(values.getUserId(), values.getProductId(), values.getVariantId());
    	var entity = cartItemRepo.findById(id).orElseThrow(() -> new ApplicationException("Cart item not found"));
    	entity.setQuantity(values.getQuantity());
        cartItemRepo.save(entity);
    }

    @Override
    public void deleteByUser(long userId) {
        cartItemRepo.deleteById_UserId(userId);
    }

    @Override
    public void deleteAll(List<CartItem.ID> items) {
    	var list = items.stream().map(v -> {
    		return new CartItemEntity.ID(v.getUserId(), v.getProductId(), v.getVariantId());
    	}).toList();
        cartItemRepo.deleteAllById(list);
    }

    @Override
    public void deleteByProduct(long productId) {
        cartItemRepo.deleteByProductId(productId);
    }

    @Override
    public boolean existsById(CartItem.ID id) {
    	var entityId = new CartItemEntity.ID(id.getUserId(), id.getProductId(), id.getVariantId());
        return cartItemRepo.existsById(entityId);
    }

    @Override
    public long countByUser(long userId) {
        return cartItemRepo.countById_UserId(userId);
    }
    
    @Override
    public CartItem findById(CartItem.ID id) {
    	var entityId = new CartItemEntity.ID(id.getUserId(), id.getProductId(), id.getVariantId());
    	return cartItemRepo.findById(entityId).map(CartItemMapper::toDomain).orElse(null);
    }

    @Override
    public List<CartItem> findByUser(long userId) {
        return cartItemRepo.findById_UserId(userId).stream().map(CartItemMapper::toDomain).collect(Collectors.toList());
    }

}
