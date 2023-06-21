package com.shoppingcenter.app.controller.subscription;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.subscription.dto.SubscriptionPromoDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.subscription.SubscriptionPromo;
import com.shoppingcenter.domain.subscription.SubscriptionPromoDao;
import com.shoppingcenter.domain.subscription.SubscriptionPromoQuery;

@Service
public class SubscriptionPromoService {

	@Autowired
	private SubscriptionPromoDao subscriptionPromoDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public SubscriptionPromoDTO create(SubscriptionPromoDTO dto) {
		if (!StringUtils.hasText(dto.getCode())) {
			throw new ApplicationException("Required promo code");
		}
		
		if (subscriptionPromoDao.existsByCode(dto.getCode())) {
			throw new ApplicationException("Promo code duplicated");
		}
		
		var source = subscriptionPromoDao.create(modelMapper.map(dto, SubscriptionPromo.class));
		
		return modelMapper.map(source, SubscriptionPromoDTO.class);
	}
	
	@Transactional
	public SubscriptionPromoDTO update(SubscriptionPromoDTO dto) {
		var source = subscriptionPromoDao.update(modelMapper.map(dto, SubscriptionPromo.class));
		return modelMapper.map(source, SubscriptionPromoDTO.class);
	}
	
	public void delete(long id) {
		subscriptionPromoDao.deleteById(id);
	}
	
	public SubscriptionPromoDTO findById(long id) {
		var source = subscriptionPromoDao.findById(id);
		
		if (source != null) {
			return modelMapper.map(source, SubscriptionPromoDTO.class);
		}
		
		return null;
	}
	
	public SubscriptionPromoDTO findByCode(String code) {
		var source = subscriptionPromoDao.findByCode(code);
		
		if (source != null) {
			return modelMapper.map(source, SubscriptionPromoDTO.class);
		}
		
		return null;
	}
	
	public PageDataDTO<SubscriptionPromoDTO> findAll(SubscriptionPromoQuery query) {
		var source = subscriptionPromoDao.findAll(query);
		
		return modelMapper.map(source, SubscriptionPromoDTO.pageType());
	}
	
}
