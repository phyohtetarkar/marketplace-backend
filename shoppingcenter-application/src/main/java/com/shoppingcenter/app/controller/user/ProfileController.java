package com.shoppingcenter.app.controller.user;

import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.controller.MultipartFileMapper;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.order.OrderFacade;
import com.shoppingcenter.app.controller.order.dto.OrderDTO;
import com.shoppingcenter.app.controller.product.FavoriteProductFacade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.shop.ShopService;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shoppingcart.ShoppingCartFacade;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.user.dto.PhoneNumberUpdateDTO;
import com.shoppingcenter.app.controller.user.dto.ProfileStatisticDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.order.Order;
import com.shoppingcenter.domain.order.OrderQuery;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private FavoriteProductFacade favoriteProductFacade;

	@Autowired
	private ShoppingCartFacade shoppingCartFacade;

	@Autowired
	private OrderFacade orderFacade;

	@Autowired
	private AuthenticationContext authentication;

	@PutMapping
	public void update(@RequestBody UserEditDTO user) {
		user.setId(authentication.getUserId());
		userService.update(user);
	}

	@PutMapping("image")
	public void uploadImage(@RequestPart MultipartFile file) {
		userService.uploadImage(authentication.getUserId(), MultipartFileMapper.toUploadFile(file));
	}
	
	@PutMapping("phone")
	public void updatePhoneNumber(@RequestBody PhoneNumberUpdateDTO dto) {
		dto.setUserId(authentication.getUserId());
		userService.changePhoneNumber(dto);
	}
	
	@PutMapping("change-password")
	public void changePassword(@RequestParam("old-password") String oldPassword, @RequestParam("new-password") String newPassword) {
		userService.changePassword(authentication.getUserId(), oldPassword, newPassword);
	}

	@GetMapping
	public UserDTO getLoginUser() {
		return userService.findById(authentication.getUserId());
	}
	
	@GetMapping("statistic")
	public ProfileStatisticDTO getStatistic() {
		return userService.getProfileStatisitc(authentication.getUserId());
	}

	@GetMapping("favorite-products")
	public PageDataDTO<ProductDTO> getFavoriteProducts(@RequestParam(required = false) Integer page) {
		return favoriteProductFacade.findByUser(authentication.getUserId(), page);
	}

	@GetMapping("shops")
	public PageDataDTO<ShopDTO> getMyShops(@RequestParam(required = false) Integer page) {
		return shopService.findByUser(authentication.getUserId(), page);
	}

	@GetMapping("cart-items")
	public List<CartItemDTO> findCartItems() {
		return shoppingCartFacade.findByUser(authentication.getUserId());
	}

	@GetMapping("cart-count")
	public long getCartCount() {
		return shoppingCartFacade.countByUser(authentication.getUserId());
	}

	@GetMapping("orders")
	public PageDataDTO<OrderDTO> getOrders(
			@RequestParam(required = false) String date,
			@RequestParam(required = false) Order.Status status,
			@RequestParam(required = false, name = "time-zone") String timeZone,
			@RequestParam(required = false) Integer page) {

		var query = OrderQuery.builder()
				.userId(authentication.getUserId())
				.date(date)
				.status(status)
				.timeZone(timeZone == null ? ZoneOffset.systemDefault().getId() : timeZone)
				.page(page)
				.build();
		return orderFacade.getOrders(query);
	}

}
