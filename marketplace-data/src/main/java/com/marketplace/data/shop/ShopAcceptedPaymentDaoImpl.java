package com.marketplace.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopAcceptedPayment;
import com.marketplace.domain.shop.ShopAcceptedPaymentInput;
import com.marketplace.domain.shop.dao.ShopAcceptedPaymentDao;

@Repository
public class ShopAcceptedPaymentDaoImpl implements ShopAcceptedPaymentDao {

	@Autowired
	private ShopAcceptedPaymentRepo repo;

	@Autowired
	private ShopRepo shopRepo;

	@Override
	public void save(ShopAcceptedPaymentInput values) {
		var entity = repo.findById(values.getId()).orElseGet(ShopAcceptedPaymentEntity::new);
		entity.setAccountType(values.getAccountType());
		entity.setAccountName(values.getAccountName());
		entity.setAccountNumber(values.getAccountNumber());
		entity.setShop(shopRepo.getReferenceById(values.getShopId()));

		repo.save(entity);
	}
	
	@Override
	public void saveAll(List<ShopAcceptedPaymentInput> payments) {
		var entities = payments.stream().map(p -> {
			var entity = repo.findById(p.getId()).orElseGet(ShopAcceptedPaymentEntity::new);
			entity.setAccountType(p.getAccountType());
			entity.setAccountName(p.getAccountName());
			entity.setAccountNumber(p.getAccountNumber());
			entity.setShop(shopRepo.getReferenceById(p.getShopId()));
			return entity;
		}).toList();
		
		repo.saveAll(entities);
	}

	@Override
	public void delete(long id) {
		repo.deleteById(id);
	}

	@Override
	public List<ShopAcceptedPayment> findAllByShop(long shopId) {
		return repo.findByShopId(shopId).stream()
				.map(ShopAcceptedPaymentMapper::toDomain)
				.toList();
	}

}
