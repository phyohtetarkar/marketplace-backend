package com.shoppingcenter.data.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.shop.ShopMember;
import com.shoppingcenter.domain.shop.dao.ShopMemberDao;

@Repository
public class ShopMemberDaoImpl implements ShopMemberDao {

    @Autowired
    private ShopMemberRepo shopMemberRepo;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public long save(ShopMember member) {
        var entity = shopMemberRepo.findById(member.getId()).orElseGet(ShopMemberEntity::new);
        entity.setRole(member.getRole().name());
        if (entity.getId() == 0) {
            entity.setUser(userRepo.getReferenceById(member.getUserId()));
            entity.setShop(shopRepo.getReferenceById(member.getShopId()));
        }

        var result = shopMemberRepo.save(entity);

        return result.getId();
    }

    @Override
    public void delete(long id) {
        shopMemberRepo.deleteById(id);
    }

    @Override
    public boolean existsByShopAndUser(long shopId, String userId) {
        return shopMemberRepo.existsByShop_IdAndUser_Id(shopId, userId);
    }

}
