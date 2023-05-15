package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.app.controller.MultipartFileMapper;
import com.shoppingcenter.app.controller.PageDataDTO;
import com.shoppingcenter.app.controller.product.ProductFacade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopCreateDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSaleHistoryDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSettingDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopStatisticDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops")
@Tag(name = "Shop")
public class ShopController {

	@Autowired
	private ShopFacade shopFacade;

	@Autowired
	private ProductFacade productFacade;

	@Autowired
	private ValidateShopMemberUseCase validateShopMemberUseCase;

	@Autowired
	private AuthenticationContext authentication;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void create(@ModelAttribute ShopCreateDTO shop) {
		shop.setUserId(authentication.getUserId());
		shopFacade.create(shop);
	}

	@PutMapping("{id:\\d+}/general")
	public ShopDTO updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneralDTO general) {
		return shopFacade.updateGeneralInfo(general);
	}

	@PutMapping("{id:\\d+}/contact")
	public void updateContact(@PathVariable long id, @RequestBody ShopContactDTO contact) {
		contact.setShopId(id);
		shopFacade.updateContact(contact);
	}

	@PutMapping("{id:\\d+}/setting")
	public void updateSetting(@PathVariable long id, @RequestBody ShopSettingDTO setting) {
		setting.setShopId(id);
		shopFacade.updateSetting(setting);
	}

	@PutMapping("{id:\\d+}/logo")
	public void uploadLogo(@PathVariable long id, @RequestPart MultipartFile file) {
		shopFacade.uploadLogo(id, MultipartFileMapper.toUploadFile(file));
	}

	@PutMapping("{id:\\d+}/cover")
	public void uploadCover(@PathVariable long id, @RequestPart MultipartFile file) {
		shopFacade.uploadCover(id, MultipartFileMapper.toUploadFile(file));
	}

	@GetMapping("{id:\\d+}/statistic")
	public ShopStatisticDTO getStatistic(@PathVariable long id) {
		return shopFacade.getShopStatistic(id);
	}

	@GetMapping("{id:\\d+}/setting")
	public ShopSettingDTO getSetting(@PathVariable long id) {
		return shopFacade.getShopSetting(id);
	}

	@GetMapping("{slugOrId}")
	public ShopDTO findBySlug(@PathVariable String slugOrId, Authentication authentication) {
		if (slugOrId.matches("[0-9]+") && authentication != null && authentication.isAuthenticated()) {
			return shopFacade.findById(Long.parseLong(slugOrId));
		}
		return shopFacade.findBySlug(slugOrId);
	}

	@GetMapping("{id:\\d+}/pending-order-count")
	public long getPendingOrderCount(@PathVariable long id) {
		return shopFacade.getPendingOrderCount(id);
	}

	@GetMapping("{id:\\d+}/monthly-sales")
	public List<ShopSaleHistoryDTO> getMonthlySale(@PathVariable long id, @RequestParam int year) {
		return shopFacade.getMonthlySale(id, year);
	}

	@GetMapping
	public PageDataDTO<ShopDTO> findAll(@RequestParam(required = false) String q,
			@RequestParam(required = false) Integer page) {
		var query = ShopQuery.builder().q(q).disabled(false).expired(false).page(page).build();
		return shopFacade.findAll(query);
	}

	@GetMapping("{id:\\d+}/products")
	public PageDataDTO<ProductDTO> findProducts(@PathVariable long id, @RequestParam(required = false) String q,
			@RequestParam(required = false, name = "brand") String[] brands,
			@RequestParam(required = false, name = "category-id") Integer categoryId,
			@RequestParam(required = false, name = "discount-id") Long discountId,
			@RequestParam(required = false) Integer page) {

		validateShopMemberUseCase.apply(id, authentication.getUserId());

		var query = ProductQuery.builder().q(q).categoryId(categoryId).shopId(id).discountId(discountId).brands(brands)
				.page(page).build();

		return productFacade.findAll(query);
	}

}
