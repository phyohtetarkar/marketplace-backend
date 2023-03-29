package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopEditDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopInsightsDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.ShopContact;
import com.shoppingcenter.domain.shop.ShopGeneral;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.usecase.CreateShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetAllShopUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByIdUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopBySlugUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopByUserUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopHintsUseCase;
import com.shoppingcenter.domain.shop.usecase.GetShopInsightsUseCase;
import com.shoppingcenter.domain.shop.usecase.SaveShopContactUseCase;
import com.shoppingcenter.domain.shop.usecase.UpdateShopBasicInfoUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopCoverUseCase;
import com.shoppingcenter.domain.shop.usecase.UploadShopLogoUseCase;

@Facade
public class ShopFacadeImpl implements ShopFacade {

    @Autowired
    private CreateShopUseCase createShopUseCase;

    @Autowired
    private UpdateShopBasicInfoUseCase updateShopBasicInfoUseCase;

    @Autowired
    private SaveShopContactUseCase saveShopContactUseCase;

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
    @Override
    public void create(ShopEditDTO shop) {
        createShopUseCase.apply(modelMapper.map(shop, Shop.class));
    }

    @Transactional
    @Override
    public ShopDTO updateGeneralInfo(ShopGeneralDTO general) {
        var shop = updateShopBasicInfoUseCase.apply(modelMapper.map(general, ShopGeneral.class));
        return modelMapper.map(shop, ShopDTO.class);
    }

    @Transactional
    @Override
    public void updateContact(ShopContactDTO contact) {
        saveShopContactUseCase.apply(modelMapper.map(contact, ShopContact.class));
    }

    @Transactional
    @Override
    public void uploadLogo(long shopId, UploadFile file) {
        uploadShopLogoUseCase.apply(shopId, file);
    }

    @Transactional
    @Override
    public void uploadCover(long shopId, UploadFile file) {
        uploadShopCoverUseCase.apply(shopId, file);
    }

    @Transactional
    @Override
    public void updateStatus(long shopId, Status status) {
        // TODO Auto-generated method stub

    }

    @Transactional
    @Override
    public void delete(long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public ShopInsightsDTO getShopInsights(long shopId) {
        return modelMapper.map(getShopInsightsUseCase.apply(shopId), ShopInsightsDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDTO findById(long id) {
        return modelMapper.map(getShopByIdUseCase.apply(id), ShopDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public ShopDTO findBySlug(String slug) {
        return modelMapper.map(getShopBySlugUseCase.apply(slug), ShopDTO.class);
    }

    @Override
    public List<String> getHints(String q) {
        return getShopHintsUseCase.apply(q);
    }

    @Override
    public PageData<ShopDTO> findByUser(String userId, Integer page) {
        return modelMapper.map(getShopByUserUseCase.apply(userId, page), ShopDTO.pageType());
    }

    @Override
    public PageData<ShopDTO> findAll(ShopQuery query) {
        return modelMapper.map(getAllShopUseCase.apply(query), ShopDTO.pageType());
    }

}
