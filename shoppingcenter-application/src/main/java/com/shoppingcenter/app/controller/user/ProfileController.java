package com.shoppingcenter.app.controller.user;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.order.OrderFacade;
import com.shoppingcenter.app.controller.order.dto.OrderDTO;
import com.shoppingcenter.app.controller.product.FavoriteProductFacade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.shop.ShopFacade;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shoppingcart.ShoppingCartFacade;
import com.shoppingcenter.app.controller.shoppingcart.dto.CartItemDTO;
import com.shoppingcenter.app.controller.user.dto.UserDTO;
import com.shoppingcenter.app.controller.user.dto.UserEditDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.UploadFile;
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
    private UserFacade userFacade;

    @Autowired
    private ShopFacade shopFacade;

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
        userFacade.update(user);
    }

    @PostMapping(value = "image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadImage(@RequestPart MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return;
            }

            var uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            userFacade.uploadImage(authentication.getUserId(), uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @GetMapping
    public UserDTO getLoginUser() {
        return userFacade.findById(authentication.getUserId());
    }

    @GetMapping("favorite-products")
    public PageDataDTO<ProductDTO> getFavoriteProducts(
            @RequestParam(required = false) Integer page) {
        return favoriteProductFacade.findByUser(authentication.getUserId(), page);
    }
    
    @GetMapping("shops")
    public PageDataDTO<ShopDTO> getMyShops(
            @RequestParam(required = false) Integer page) {
        return shopFacade.findByUser(authentication.getUserId(), page);
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
			@RequestParam(name = "time-zone", required = false) String timeZone,
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
