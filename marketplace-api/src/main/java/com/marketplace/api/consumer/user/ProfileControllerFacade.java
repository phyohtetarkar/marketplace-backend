package com.marketplace.api.consumer.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.MultipartFileConverter;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.ConsumerDataMapper;
import com.marketplace.api.consumer.order.OrderDTO;
import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.shop.ShopDTO;
import com.marketplace.api.consumer.shoppingcart.CartItemDTO;
import com.marketplace.domain.order.OrderQuery;
import com.marketplace.domain.order.usecase.GetAllOrderByQueryUseCase;
import com.marketplace.domain.product.usecase.CheckFavoriteProductUseCase;
import com.marketplace.domain.product.usecase.GetFavoriteProductByUserUseCase;
import com.marketplace.domain.shop.usecase.GetShopByUserUseCase;
import com.marketplace.domain.shoppingcart.usecase.GetCartItemCountByUserUseCase;
import com.marketplace.domain.shoppingcart.usecase.GetCartItemsByUserUseCase;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.usecase.GetProfileStatisticUseCase;
import com.marketplace.domain.user.usecase.GetUserByIdUseCase;
import com.marketplace.domain.user.usecase.GetUserPermissionsUseCase;
import com.marketplace.domain.user.usecase.UpdateUserUseCase;
import com.marketplace.domain.user.usecase.UploadUserImageUseCase;

@Component
public class ProfileControllerFacade {

	@Autowired
	private GetUserByIdUseCase getUserByIdUseCase;

	@Autowired
	private UpdateUserUseCase updateUserUseCase;
	
	@Autowired
	private UploadUserImageUseCase uploadUserImageUseCase;

	@Autowired
	private GetProfileStatisticUseCase getProfileStatisticUseCase;
	
	@Autowired
	private GetFavoriteProductByUserUseCase getFavoriteProductByUserUseCase;
	
	@Autowired
	private GetCartItemsByUserUseCase getCartItemsByUserUseCase;
	
	@Autowired
	private GetShopByUserUseCase getShopByUserUseCase;
	
	@Autowired
	private GetAllOrderByQueryUseCase getAllOrderByQueryUseCase;
	
	@Autowired
	private GetCartItemCountByUserUseCase getCartItemCountByUserUseCase;
	
	@Autowired
    private CheckFavoriteProductUseCase checkFavoriteProductUseCase;
	
	@Autowired
	private GetUserPermissionsUseCase getUserPermissionsUseCase;
	
	@Autowired
	private ConsumerDataMapper mapper;

	public void update(UserEditDTO user) {
		updateUserUseCase.apply(mapper.map(user));
	}
	
	public void uploadImage(long userId, MultipartFile file) {
		uploadUserImageUseCase.apply(userId, MultipartFileConverter.toUploadFile(file));
	}

	public ProfileStatisticDTO getProfileStatisitc(long userId) {
		return mapper.map(getProfileStatisticUseCase.apply(userId));
	}

	public UserDTO findById(long id) {
		var user = getUserByIdUseCase.apply(id);
		return mapper.map(user);
	}
	
	public PageDataDTO<ProductDTO> getFavoriteProducts(long userId, Integer page) {
		var source = getFavoriteProductByUserUseCase.apply(userId, page);
		return mapper.mapProductPage(source);
	}
	
	public List<CartItemDTO> getCartItems(long userId) {
		var source = getCartItemsByUserUseCase.apply(userId);
		return mapper.mapCartItemList(source);
	}
	
	public PageDataDTO<ShopDTO> getShops(long userId, Integer page) {
		var source = getShopByUserUseCase.apply(userId, page);
		return mapper.mapShopPage(source);
	}
	
	public PageDataDTO<OrderDTO> getOrders(OrderQuery query) {
		var source = getAllOrderByQueryUseCase.apply(query);
		return mapper.mapOrderPage(source);
	}
	
	public long getCartItemCount(long userId) {
		return getCartItemCountByUserUseCase.apply(userId);
	}
	
	public boolean checkFavoriteByUser(long userId, long productId) {
        return checkFavoriteProductUseCase.apply(userId, productId);
    }
	
	public List<User.Permission> getUserPermissions(long userId) {
		return getUserPermissionsUseCase.apply(userId);
	}
}
