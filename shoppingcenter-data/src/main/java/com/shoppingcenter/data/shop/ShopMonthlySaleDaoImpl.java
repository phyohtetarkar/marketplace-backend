package com.shoppingcenter.data.shop;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.domain.shop.ShopMonthlySale;
import com.shoppingcenter.domain.shop.dao.ShopMonthlySaleDao;

@Repository
public class ShopMonthlySaleDaoImpl implements ShopMonthlySaleDao {
	
	@Autowired
	private ShopMonthlySaleRepo repo;

	@Override
	public void save(ShopMonthlySale sale) {
		var id = new ShopMonthlySaleEntity.ID(sale.getShopId(), sale.getYear(), sale.getMonth());
		var entity = repo.findById(id).orElseGet(ShopMonthlySaleEntity::new);
		entity.setId(id);
		
		entity.setTotalSale(sale.getTotalSale());
		
		repo.save(entity);
	}

	@Override
	public double getTotalSaleByShop(long shopId) {
		return repo.totalSaleByShop(shopId);
	}

	@Override
	public ShopMonthlySale findByShopAndYearMonth(long shopId, YearMonth ym) {
		var id = new ShopMonthlySaleEntity.ID(shopId, ym);
		return repo.findById(id).map(ShopMonthlySaleMapper::toDomain).orElse(null);
	}

	@Override
	public List<ShopMonthlySale> findByShopAndYear(long shopId, int year) {
		return repo.findById_ShopIdAndId_Year(shopId, year).stream()
				.map(ShopMonthlySaleMapper::toDomain).toList();
	}

}
