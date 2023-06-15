package com.shoppingcenter.app.controller.misc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.controller.misc.dto.DashboardDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSubscriptionDTO;
import com.shoppingcenter.domain.misc.DashboardDataDao;

@Service
public class DashboardService {

	@Autowired
	private DashboardDataDao dashboardDataDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional(readOnly = true)
	public DashboardDataDTO getDashboardData() {
		var dto = new DashboardDataDTO();
		dto.setStatistic(dashboardDataDao.getStatistic());
		dto.setRecentSubscriptions(modelMapper.map(dashboardDataDao.findLatest10SuccessfulSubscription(), ShopSubscriptionDTO.listType()));
		dto.setPendingShops(modelMapper.map(dashboardDataDao.findLatest10PendingShop(), ShopDTO.listType()));
		return dto;
	}
	
}
