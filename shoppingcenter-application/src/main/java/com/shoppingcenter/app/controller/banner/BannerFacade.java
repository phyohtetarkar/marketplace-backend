package com.shoppingcenter.app.controller.banner;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.MultipartFileMapper;
import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.app.controller.banner.dto.BannerEditDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.banner.Banner;
import com.shoppingcenter.domain.banner.usecase.DeleteBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.GetAllBannerUseCase;
import com.shoppingcenter.domain.banner.usecase.GetBannerByIdUseCase;
import com.shoppingcenter.domain.banner.usecase.SaveBannerUseCase;

@Facade
public class BannerFacade {

    @Autowired
    private SaveBannerUseCase saveBannerUseCase;

    @Autowired
    private DeleteBannerUseCase deleteBannerUseCase;

    @Autowired
    private GetBannerByIdUseCase getBannerByIdUseCase;

    @Autowired
    private GetAllBannerUseCase getAllBannerUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void save(BannerEditDTO banner) {
    	var file = MultipartFileMapper.toUploadFile(banner.getFile());
        saveBannerUseCase.apply(modelMapper.map(banner, Banner.class), file);
    }

    @Transactional
    public void delete(int id) {
        deleteBannerUseCase.apply(id);
    }

    public BannerDTO findById(int id) {
    	var result = getBannerByIdUseCase.apply(id);
    	if (result == null) {
    		throw new ApplicationException("Banner not found");
    	}
        return modelMapper.map(result, BannerDTO.class);
    }

    public List<BannerDTO> findAll() {
        return modelMapper.map(getAllBannerUseCase.apply(), BannerDTO.listType());
    }

}
