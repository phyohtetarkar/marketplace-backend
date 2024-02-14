package com.marketplace.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.data.user.UserRepo;
import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.ShopMemberInput;
import com.marketplace.domain.shop.dao.ShopMemberDao;

@Repository
public class ShopMemberDaoImpl implements ShopMemberDao {

    @Autowired
    private ShopMemberRepo shopMemberRepo;
    
    @Autowired
    private ShopRepo shopRepo;
    
    @Autowired
    private UserRepo userRepo;

    @Override
    public ShopMember save(ShopMemberInput values) {
        var id = new ShopMemberEntity.ID(values.getShopId(), values.getUserId());
        var entity = shopMemberRepo.findById(id).orElseGet(ShopMemberEntity::new);
        entity.setShop(shopRepo.getReferenceById(values.getShopId()));
        entity.setUser(userRepo.getReferenceById(values.getUserId()));
        entity.setRole(values.getRole());

        var result = shopMemberRepo.save(entity);

        return ShopMemberMapper.toDomain(result);
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
    	return shopMemberRepo.findById(id).map(ShopMemberMapper::toDomain).orElse(null);
    }
    
    @Override
    public List<ShopMember> findByShop(long shopId) {
    	return shopMemberRepo.findByShopId(shopId).stream()
    			.map(ShopMemberMapper::toDomain)
    			.toList();
    }

}
