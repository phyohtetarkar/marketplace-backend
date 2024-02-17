package com.marketplace.api.consumer.user;

import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.api.AuthenticationUtil;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.order.OrderDTO;
import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.shop.ShopDTO;
import com.marketplace.api.consumer.shoppingcart.CartItemDTO;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.OrderQuery;
import com.marketplace.domain.user.User;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Consumer")
public class ProfileController {
	
	@Autowired
	private ProfileControllerFacade profileFacade;

	@PutMapping
	public void update(@RequestBody UserEditDTO body) {
		body.setUserId(AuthenticationUtil.getAuthenticatedUserId());
		profileFacade.update(body);
	}
	
	@PutMapping("image")
	public void uploadImage(@RequestPart MultipartFile file) {
		profileFacade.uploadImage(AuthenticationUtil.getAuthenticatedUserId(), file);
	}
	
	@GetMapping
	public UserDTO getLoginUser() {
		return profileFacade.findById(AuthenticationUtil.getAuthenticatedUserId());
	}
	
	@GetMapping("permissions")
	public List<User.Permission> getPermissions() {
		return profileFacade.getUserPermissions(AuthenticationUtil.getAuthenticatedUserId());
	}
	
	@GetMapping("statistic")
	public ProfileStatisticDTO getStatistic() {
		return profileFacade.getProfileStatisitc(AuthenticationUtil.getAuthenticatedUserId());
	}
	
	@GetMapping("favorite-products")
	public PageDataDTO<ProductDTO> getFavoriteProducts(@RequestParam(required = false) Integer page) {
		return profileFacade.getFavoriteProducts(AuthenticationUtil.getAuthenticatedUserId(), page);
	}
	
	@GetMapping("favorite-products/{productId:\\d+}/check")
	public boolean checkFavoriteProduct(@PathVariable long productId) {
		return profileFacade.checkFavoriteByUser(AuthenticationUtil.getAuthenticatedUserId(), productId);
	}

	@GetMapping("shops")
	public PageDataDTO<ShopDTO> getMyShops(@RequestParam(required = false) Integer page) {
		return profileFacade.getShops(AuthenticationUtil.getAuthenticatedUserId(), page);
	}

	@GetMapping("cart-items")
	public List<CartItemDTO> findCartItems() {
		return profileFacade.getCartItems(AuthenticationUtil.getAuthenticatedUserId());
	}

	@GetMapping("cart-count")
	public long getCartCount() {
		return profileFacade.getCartItemCount(AuthenticationUtil.getAuthenticatedUserId());
	}

	@GetMapping("orders")
	public PageDataDTO<OrderDTO> getOrders(
			@RequestParam(required = false) String date,
			@RequestParam(required = false) Order.Status status,
			@RequestParam(required = false, name = "time-zone") String timeZone,
			@RequestParam(required = false) Integer page) {

		var query = OrderQuery.builder()
				.userId(AuthenticationUtil.getAuthenticatedUserId())
				.date(date)
				.status(status)
				.timeZone(timeZone == null ? ZoneOffset.systemDefault().getId() : timeZone)
				.page(page)
				.build();
		
		return profileFacade.getOrders(query);
	}
}
