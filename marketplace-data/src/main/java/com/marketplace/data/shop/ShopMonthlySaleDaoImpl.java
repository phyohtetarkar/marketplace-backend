package com.marketplace.data.shop;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketplace.domain.shop.ShopMonthlySale;
import com.marketplace.domain.shop.dao.ShopMonthlySaleDao;

@Repository
public class ShopMonthlySaleDaoImpl implements ShopMonthlySaleDao {

	@Autowired
	private ShopMonthlySaleRepo repo;

	@Autowired
	private ShopRepo shopRepo;

	@Override
	public void save(long shopId, ShopMonthlySale sale) {
		var id = new ShopMonthlySaleEntity.ID(shopId, sale.getYear(), sale.getMonth());
		var entity = repo.findById(id).orElseGet(ShopMonthlySaleEntity::new);
		entity.setId(id);
		entity.setTotalSale(sale.getTotalSale());
		entity.setShop(shopRepo.getReferenceById(shopId));

		repo.save(entity);
	}

	@Override
	public void updateTotalSale(long shopId, YearMonth yearMonth, BigDecimal value) {
		var id = new ShopMonthlySaleEntity.ID(shopId, yearMonth);
		repo.updateTotalSale(value, id);
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
		return repo.findById_ShopIdAndId_Year(shopId, year).stream().map(ShopMonthlySaleMapper::toDomain).toList();
	}

}
