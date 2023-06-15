package com.shoppingcenter.data.misc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoppingcenter.data.product.ProductRepo;
import com.shoppingcenter.data.shop.ShopMapper;
import com.shoppingcenter.data.shop.ShopRepo;
import com.shoppingcenter.data.subscription.ShopSubscriptionMapper;
import com.shoppingcenter.data.subscription.ShopSubscriptionRepo;
import com.shoppingcenter.data.user.UserRepo;
import com.shoppingcenter.domain.misc.DashboardDataDao;
import com.shoppingcenter.domain.misc.Statistic;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.subscription.ShopSubscription;

@Repository
public class DashboardDataDaoImpl implements DashboardDataDao {
	
	@Autowired
	private ShopSubscriptionRepo shopSubscriptionRepo;
	
	@Autowired
	private ShopRepo shopRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public Statistic getStatistic() {
		var statistic = new Statistic();
		statistic.setTotalSubscription(shopSubscriptionRepo.getTotalSubscriptionPrice());
		statistic.setTotalShop(shopRepo.count());
		statistic.setTotalProduct(productRepo.count());
		statistic.setTotalUser(userRepo.count());
		return statistic;
	}

	@Override
	public List<ShopSubscription> findLatest10SuccessfulSubscription() {
		return shopSubscriptionRepo.findTop10ByStatusOrderByModifiedAtDesc(ShopSubscription.Status.SUCCESS).stream()
				.map(ShopSubscriptionMapper::toDomain)
				.toList();
	}

	@Override
	public List<Shop> findLatest10PendingShop() {
		return shopRepo.findTop10ByStatusOrderByCreatedAtDesc(Shop.Status.PENDING).stream()
				.map(ShopMapper::toDomainCompat)
				.toList();
	}

}
