package com.shoppingcenter.app.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.product.dto.FavoriteProductDTO;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.product.usecase.AddProductToFavoriteUseCase;
import com.shoppingcenter.domain.product.usecase.CheckFavoriteProductUseCase;
import com.shoppingcenter.domain.product.usecase.GetFavoriteProductByUserUseCase;
import com.shoppingcenter.domain.product.usecase.RemoveProductFromFavoriteUseCase;

@Facade
@Transactional
public class FavoriteProductFacadeImpl implements FavoriteProductFacade {

    @Autowired
    private AddProductToFavoriteUseCase addProductToFavoriteUseCase;

    @Autowired
    private RemoveProductFromFavoriteUseCase removeProductFromFavoriteUseCase;

    @Autowired
    private CheckFavoriteProductUseCase checkFavoriteProductUseCase;

    @Autowired
    private GetFavoriteProductByUserUseCase getFavoriteProductByUserUseCase;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void add(long userId, long productId) {
        addProductToFavoriteUseCase.apply(userId, productId);
    }

    @Override
    public void remove(long userId, long productId) {
        removeProductFromFavoriteUseCase.apply(userId, productId);
    }

    @Override
    public boolean checkFavorite(long userId, long productId) {
        return checkFavoriteProductUseCase.apply(userId, productId);
    }

    @Override
    public PageData<FavoriteProductDTO> findByUser(long userId, Integer page) {
        return modelMapper.map(getFavoriteProductByUserUseCase.apply(userId, page), FavoriteProductDTO.pageType());
    }

}
