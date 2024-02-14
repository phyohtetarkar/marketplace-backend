package com.marketplace.api.vendor.shop;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.AbstractControllerFacade;
import com.marketplace.api.MultipartFileConverter;
import com.marketplace.domain.order.usecase.GetPendingOrderCountByShopUseCase;
import com.marketplace.domain.shop.ShopAcceptedPaymentInput;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.ShopCreateInput;
import com.marketplace.domain.shop.ShopSettingInput;
import com.marketplace.domain.shop.ShopUpdateInput;
import com.marketplace.domain.shop.usecase.CreateShopUseCase;
import com.marketplace.domain.shop.usecase.DeleteShopAcceptedPaymentUseCase;
import com.marketplace.domain.shop.usecase.GetMonthlySaleByShopUseCase;
import com.marketplace.domain.shop.usecase.GetShopByIdUseCase;
import com.marketplace.domain.shop.usecase.GetShopSettingUseCase;
import com.marketplace.domain.shop.usecase.GetShopStatisticUseCase;
import com.marketplace.domain.shop.usecase.SaveShopAcceptedPaymentUseCase;
import com.marketplace.domain.shop.usecase.SaveShopSettingUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopContactUseCase;
import com.marketplace.domain.shop.usecase.UpdateShopUseCase;
import com.marketplace.domain.shop.usecase.UploadShopCoverUseCase;
import com.marketplace.domain.shop.usecase.UploadShopLogoUseCase;

@Component
public class ShopControllerFacade extends AbstractControllerFacade {

	@Autowired
	private CreateShopUseCase createShopUseCase;
	
	@Autowired
	private UpdateShopUseCase updateShopUseCase;
	
	@Autowired
    private UpdateShopContactUseCase saveShopContactUseCase;

    @Autowired
    private UploadShopLogoUseCase uploadShopLogoUseCase;

    @Autowired
    private UploadShopCoverUseCase uploadShopCoverUseCase;
    
    @Autowired
    private GetShopByIdUseCase getShopByIdUseCase;
    
    @Autowired
    private GetShopStatisticUseCase getShopStatisticUseCase;
    
    @Autowired
    private SaveShopSettingUseCase saveShopSettingUseCase;
    
    @Autowired
	private SaveShopAcceptedPaymentUseCase saveShopAcceptedPaymentUseCase;
	
	@Autowired
	private DeleteShopAcceptedPaymentUseCase deleteShopAcceptedPaymentUseCase;
    
    @Autowired
    private GetShopSettingUseCase getShopSettingUseCase;
    
    @Autowired
    private GetMonthlySaleByShopUseCase getMonthlySaleByShopUseCase;
    
    @Autowired
    private GetPendingOrderCountByShopUseCase getPendingOrderCountByShopUseCase;
    
    public void create(ShopCreateDTO values) {
        createShopUseCase.apply(map(values, ShopCreateInput.class));
    }

    public ShopDTO update(ShopUpdateDTO values) {
        var source = updateShopUseCase.apply(map(values, ShopUpdateInput.class));
        return modelMapper.map(source, ShopDTO.class);
    }
	
    public void updateContact(ShopContactUpdateDTO values) {
        saveShopContactUseCase.apply(map(values, ShopContactInput.class));
    }
    
    public void updateSetting(ShopSettingDTO values) {
    	saveShopSettingUseCase.apply(map(values, ShopSettingInput.class));
    }

    public void uploadLogo(long shopId, MultipartFile file) {
        uploadShopLogoUseCase.apply(shopId, MultipartFileConverter.toUploadFile(file));
    }

    public void uploadCover(long shopId, MultipartFile file) {
        uploadShopCoverUseCase.apply(shopId, MultipartFileConverter.toUploadFile(file));
    }
    
    public void saveAcceptedPayment(long shopId, ShopAcceptedPaymentDTO values) {
    	ShopAcceptedPaymentInput input = map(values, ShopAcceptedPaymentInput.class);
    	saveShopAcceptedPaymentUseCase.apply(shopId, Arrays.asList(input));
    }
    
    public void deleteAcceptedPayment(long id) {
    	deleteShopAcceptedPaymentUseCase.apply(id);
    }
    
    public long getPendingOrderCount(long shopId) {
    	return getPendingOrderCountByShopUseCase.apply(shopId);
    }
    
    public ShopDTO findById(long id) {
    	var source = getShopByIdUseCase.apply(id);
        return map(source, ShopDTO.class);
    }
    
    public ShopStatisticDTO getShopStatistic(long shopId) {
    	var source = getShopStatisticUseCase.apply(shopId);
    	return map(source, ShopStatisticDTO.class);
    }
    
    public ShopSettingDTO getShopSetting(long shopId) {
    	var source = getShopSettingUseCase.apply(shopId);
    	return map(source, ShopSettingDTO.class);
    }
    
    public List<ShopMonthlySaleDTO> getMonthlySale(long shopId, int year) {
    	return map(getMonthlySaleByShopUseCase.apply(shopId, year), ShopMonthlySaleDTO.listType());
    }
}
