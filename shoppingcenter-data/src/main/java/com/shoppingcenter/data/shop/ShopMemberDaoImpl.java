package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.user.UserMapper;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

@Repository
public class ShopMemberDaoImpl implements ShopMemberDao {

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Override
    public void save(ShopMember member) {
        var id = new ShopMemberEntity.ID(member.getShopId(), member.getUserId());
        var entity = shopMemberRepo.findById(id).orElseGet(ShopMemberEntity::new);
        entity.setId(id);
        entity.setRole(member.getRole());
        // entity.setUser(userRepo.getReferenceById(member.getUserId()));
        // entity.setShop(shopRepo.getReferenceById(member.getShopId()));

        shopMemberRepo.save(entity);

    }

    @Override
    public void delete(long shopId, long userId) {
        var id = new ShopMemberEntity.ID(shopId, userId);
        shopMemberRepo.deleteById(id);
    }

    @Override
    public boolean existsByShopAndUser(long shopId, long userId) {
        var id = new ShopMemberEntity.ID(shopId, userId);
        return shopMemberRepo.existsById(id);
    }
    
    @Override
    public long getCountByUser(long userId) {
    	return shopMemberRepo.countByUserId(userId);
    }
    
    @Override
    public ShopMember findByShopAndUser(long shopId, long userId) {
    	var id = new ShopMemberEntity.ID(shopId, userId);
    	return shopMemberRepo.findById(id).map(e -> {
    		var member = new ShopMember();
    		member.setShopId(shopId);
    		member.setUserId(userId);
    		member.setRole(e.getRole());
    		member.setMember(UserMapper.toDomain(e.getUser()));
    		return member;
    	}).orElse(null);
    }

}
