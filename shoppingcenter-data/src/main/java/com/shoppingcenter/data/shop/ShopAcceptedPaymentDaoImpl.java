package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.ShopAcceptedPayment;
import com.shoppingcenter.domain.shop.dao.ShopAcceptedPaymentDao;

@Repository
public class ShopAcceptedPaymentDaoImpl implements ShopAcceptedPaymentDao {

	@Autowired
	private ShopAcceptedPaymentRepo repo;

	@Autowired
	private ShopRepo shopRepo;

	@Override
	public void save(ShopAcceptedPayment payment) {
		var entity = repo.findById(payment.getId()).orElseGet(ShopAcceptedPaymentEntity::new);
		entity.setAccountType(payment.getAccountType());
		entity.setAccountName(payment.getAccountName());
		entity.setAccountNumber(payment.getAccountNumber());
		entity.setShop(shopRepo.getReferenceById(payment.getShopId()));

		repo.save(entity);
	}
	
	@Override
	public void saveAll(List<ShopAcceptedPayment> payments) {
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
		return repo.findByShopId(shopId).stream().map(e -> {
			var payment = new ShopAcceptedPayment();
			payment.setId(e.getId());
			payment.setShopId(shopId);
			payment.setAccountName(e.getAccountName());
			payment.setAccountType(e.getAccountType());
			payment.setAccountNumber(e.getAccountNumber());
			return payment;
		}).toList();
	}

}
