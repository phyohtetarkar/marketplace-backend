package com.marketplace.api.admin.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.consumer.banner.BannerDTO;
import com.marketplace.domain.banner.BannerInput;
import com.marketplace.domain.banner.usecase.DeleteBannerUseCase;
import com.marketplace.domain.banner.usecase.GetAllBannerUseCase;
import com.marketplace.domain.banner.usecase.GetBannerByIdUseCase;
import com.marketplace.domain.banner.usecase.SaveBannerUseCase;

@Component
public class BannerControllerFacade extends AbstractControllerFacade {
	
	@Autowired
    private SaveBannerUseCase saveBannerUseCase;

    @Autowired
    private DeleteBannerUseCase deleteBannerUseCase;

    @Autowired
    private GetBannerByIdUseCase getBannerByIdUseCase;
    
    @Autowired
	private GetAllBannerUseCase getAllBannerUseCase;
	
	public BannerDTO save(BannerEditDTO values) {
        var source = saveBannerUseCase.apply(map(values, BannerInput.class));
        return map(source, BannerDTO.class);
    }

    public void delete(int id) {
        deleteBannerUseCase.apply(id);
    }

    public BannerDTO findById(int id) {
    	var source = getBannerByIdUseCase.apply(id);
        return modelMapper.map(source, BannerDTO.class);
    }
    
    public List<BannerDTO> findAll() {
		var source = getAllBannerUseCase.apply();
		return map(source, BannerDTO.listType());
	}
	
}
