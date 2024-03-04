package com.marketplace.api.admin.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.admin.AdminDataMapper;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.banner.BannerDTO;
import com.marketplace.domain.banner.usecase.DeleteBannerUseCase;
import com.marketplace.domain.banner.usecase.GetAllBannerUseCase;
import com.marketplace.domain.banner.usecase.GetBannerByIdUseCase;
import com.marketplace.domain.banner.usecase.SaveBannerUseCase;

@Component
public class BannerControllerFacade {
	
	@Autowired
    private SaveBannerUseCase saveBannerUseCase;

    @Autowired
    private DeleteBannerUseCase deleteBannerUseCase;

    @Autowired
    private GetBannerByIdUseCase getBannerByIdUseCase;
    
    @Autowired
	private GetAllBannerUseCase getAllBannerUseCase;
    
    @Autowired
    private AdminDataMapper adminMapper;
    
    @Autowired
    private ConsumerDataMapper consumerMapper;
	
	public BannerDTO save(BannerEditDTO values) {
        var source = saveBannerUseCase.apply(adminMapper.map(values));
        return consumerMapper.map(source);
    }

    public void delete(int id) {
        deleteBannerUseCase.apply(id);
    }

    public BannerDTO findById(int id) {
    	var source = getBannerByIdUseCase.apply(id);
        return consumerMapper.map(source);
    }
    
    public List<BannerDTO> findAll() {
		var source = getAllBannerUseCase.apply();
		return consumerMapper.mapBannerList(source);
	}
	
}
