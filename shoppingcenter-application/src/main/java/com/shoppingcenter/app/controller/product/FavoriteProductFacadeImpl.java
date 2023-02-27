package com.shoppingcenter.app.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcenter.app.annotation.Facade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
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
    public void add(String userId, long productId) {
        addProductToFavoriteUseCase.apply(userId, productId);
    }

    @Override
    public void remove(long id) {
        removeProductFromFavoriteUseCase.apply(id);
    }

    @Override
    public boolean checkFavorite(String userId, long productId) {
        return checkFavoriteProductUseCase.apply(userId, productId);
    }

    @Override
    public PageData<ProductDTO> findByUser(String userId, Integer page) {
        return modelMapper.map(getFavoriteProductByUserUseCase.apply(userId, page), ProductDTO.pageType());
    }

}
