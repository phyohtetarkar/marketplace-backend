package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopCreateDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopInsightsDTO;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopInsightsUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopBasicInfoUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopContactUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopCoverUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopLogoUseCase;

@Facade
public class ShopFacade {

    @Autowired
    private CreateShopUseCase createShopUseCase;

    @Autowired
    private UpdateShopBasicInfoUseCase updateShopBasicInfoUseCase;

    @Autowired
    private UpdateShopContactUseCase saveShopContactUseCase;

    @Autowired
    private UploadShopLogoUseCase uploadShopLogoUseCase;

    @Autowired
    private UploadShopCoverUseCase uploadShopCoverUseCase;

    @Autowired
    private GetShopBySlugUseCase getShopBySlugUseCase;

    @Autowired
    private GetShopHintsUseCase getShopHintsUseCase;

    @Autowired
    private GetShopByUserUseCase getShopByUserUseCase;

    @Autowired
    private GetAllShopUseCase getAllShopUseCase;

    @Autowired
    private GetShopInsightsUseCase getShopInsightsUseCase;

    @Autowired
    private GetShopByIdUseCase getShopByIdUseCase;
    
    
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void create(ShopCreateDTO shop) {
        createShopUseCase.apply(modelMapper.map(shop, ShopCreateInput.class));
    }

    @Transactional
    public ShopDTO updateGeneralInfo(ShopGeneralDTO general) {
        var shop = updateShopBasicInfoUseCase.apply(modelMapper.map(general, ShopGeneral.class));
        return modelMapper.map(shop, ShopDTO.class);
    }

    @Transactional
    public void updateContact(ShopContactDTO contact) {
        saveShopContactUseCase.apply(modelMapper.map(contact, ShopContact.class));
    }

    @Transactional
    public void uploadLogo(long shopId, UploadFile file) {
        uploadShopLogoUseCase.apply(shopId, file);
    }

    @Transactional
    public void uploadCover(long shopId, UploadFile file) {
        uploadShopCoverUseCase.apply(shopId, file);
    }

    @Transactional
    public void updateStatus(long shopId, Status status) {
        // TODO Auto-generated method stub

    }

    @Transactional
    public void delete(long id) {
        // TODO Auto-generated method stub
    }
    
    public ShopInsightsDTO getShopInsights(long shopId) {
        return modelMapper.map(getShopInsightsUseCase.apply(shopId), ShopInsightsDTO.class);
    }

    @Transactional(readOnly = true)
    public ShopDTO findById(long id) {
    	var result = getShopByIdUseCase.apply(id);
        return result != null ? modelMapper.map(result, ShopDTO.class) : null;
    }

    @Transactional(readOnly = true)
    public ShopDTO findBySlug(String slug) {
    	var result = getShopBySlugUseCase.apply(slug);
        return result != null ? modelMapper.map(result, ShopDTO.class) : null;
    }

    public List<String> getHints(String q) {
        return getShopHintsUseCase.apply(q);
    }

    public PageDataDTO<ShopDTO> findByUser(long userId, Integer page) {
        return modelMapper.map(getShopByUserUseCase.apply(userId, page), ShopDTO.pageType());
    }

    public PageDataDTO<ShopDTO> findAll(ShopQuery query) {
        return modelMapper.map(getAllShopUseCase.apply(query), ShopDTO.pageType());
    }
    

}
