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
import com.shoppingcenter.app.controller.shop.dto.ShopSaleHistoryDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSettingDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopStatisticDTO;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.order.usecase.GetPendingOrderCountByShopUseCase;
import com.shoppingcenter.domain.sale.usecase.GetMonthlySaleByShopUseCase;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopCreateInput;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.ShopSetting;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopSettingUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopStatisticUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopSettingUseCase;
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
    private GetShopByIdUseCase getShopByIdUseCase;
    
    @Autowired
    private GetShopStatisticUseCase getShopStatisticUseCase;
    
    @Autowired
    private SaveShopSettingUseCase saveShopSettingUseCase;
    
    @Autowired
    private GetShopSettingUseCase getShopSettingUseCase;
    
    @Autowired
    private GetMonthlySaleByShopUseCase getMonthlySaleByShopUseCase;
    
    @Autowired
    private GetPendingOrderCountByShopUseCase getPendingOrderCountByShopUseCase;
    
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
    public void updateSetting(ShopSettingDTO setting) {
    	saveShopSettingUseCase.apply(modelMapper.map(setting, ShopSetting.class));
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
    public void updateDisabled(long shopId, boolean disabled) {
    }

    @Transactional
    public void delete(long id) {
    }
    
    public long getPendingOrderCount(long shopId) {
    	return getPendingOrderCountByShopUseCase.apply(shopId);
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
    
    public ShopStatisticDTO getShopStatistic(long shopId) {
    	var result = getShopStatisticUseCase.apply(shopId);
    	return result != null ? modelMapper.map(result, ShopStatisticDTO.class) : null;
    }
    
    public ShopSettingDTO getShopSetting(long shopId) {
    	var result = getShopSettingUseCase.apply(shopId);
    	return result != null ? modelMapper.map(result, ShopSettingDTO.class) : null;
    }

    public List<String> getHints(String q) {
        return getShopHintsUseCase.apply(q);
    }
    
    public List<ShopSaleHistoryDTO> getMonthlySale(long shopId, int year) {
    	return modelMapper.map(getMonthlySaleByShopUseCase.apply(shopId, year), ShopSaleHistoryDTO.listType());
    }

    public PageDataDTO<ShopDTO> findByUser(long userId, Integer page) {
        return modelMapper.map(getShopByUserUseCase.apply(userId, page), ShopDTO.pageType());
    }

    public PageDataDTO<ShopDTO> findAll(ShopQuery query) {
        return modelMapper.map(getAllShopUseCase.apply(query), ShopDTO.pageType());
    }
    

}
