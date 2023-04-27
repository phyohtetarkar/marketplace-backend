package com.shoppingcenter.data.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.misc.City;
import com.shoppingcenter.domain.shop.dao.ShopDeliveryCityDao;

@Repository
public class ShopDeliveryCityDaoImpl implements ShopDeliveryCityDao {

	@Autowired
	private ShopDeliveryCityRepo shopDeliveryCityRepo;

	@Override
	public void saveAll(long shopId, List<City> cities) {
		var entities = cities.stream().map(c -> {
			var entity = new ShopDeliveryCityEntity();
			entity.setId(new ShopDeliveryCityEntity.ID(shopId, c.getId()));
			return entity;
		}).toList();
		
		shopDeliveryCityRepo.saveAll(entities);
	}

	@Override
	public void deleteByShop(long shopId) {
		shopDeliveryCityRepo.deleteByShopId(shopId);
	}
	
	@Override
	public boolean exists(long shopId, long cityId) {
		return shopDeliveryCityRepo.existsById(new ShopDeliveryCityEntity.ID(shopId, cityId));
	}

	@Override
	public List<City> findByShop(long shopId) {
		return shopDeliveryCityRepo.findByShopId(shopId).stream().map(e -> {
			var city = new City();
			city.setId(e.getCity().getId());
			city.setName(e.getCity().getName());
			return city;
		}).toList();
	}

}
