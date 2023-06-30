package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.shoppingcenter.app.controller.product.ProductService;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopCreateDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopMonthlySaleDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopSettingDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopStatisticDTO;
import com.shoppingcenter.domain.common.AuthenticationContext;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.shop.Shop;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.ShopQuery;
import com.shoppingcenter.domain.shop.usecase.ValidateShopMemberUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/private/shops")
@Tag(name = "Shop")
public class ShopAdminController {

    @Autowired
    private ShopService shopService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
	private ValidateShopMemberUseCase validateShopMemberUseCase;
    
    @Autowired
	private AuthenticationContext authentication;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SHOP_WRITE')")
    @PutMapping("{id:\\d+}/approve")
    public void approveShop(@PathVariable long id) {
    	shopService.updateStatus(id, Status.APPROVED);
    }
    
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SHOP_WRITE')")
    @PutMapping("{id:\\d+}/disable")
    public void disableShop(@PathVariable long id) {
    	shopService.updateStatus(id, Status.DISABLED);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public PaymentTokenResponse create(@ModelAttribute ShopCreateDTO shop) {
		shop.setUserId(authentication.getUserId());
		return shopService.create(shop);
	}

	@PutMapping("{id:\\d+}/general")
	public ShopDTO updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneralDTO general) {
		return shopService.updateGeneralInfo(general);
	}

	@PutMapping("{id:\\d+}/contact")
	public void updateContact(@PathVariable long id, @RequestBody ShopContactDTO contact) {
		contact.setShopId(id);
		shopService.updateContact(contact);
	}

	@PutMapping("{id:\\d+}/setting")
	public void updateSetting(@PathVariable long id, @RequestBody ShopSettingDTO setting) {
		setting.setShopId(id);
		shopService.updateSetting(setting);
	}

	@PutMapping("{id:\\d+}/logo")
	public void uploadLogo(@PathVariable long id, @RequestPart MultipartFile file) {
		shopService.uploadLogo(id, MultipartFileMapper.toUploadFile(file));
	}

	@PutMapping("{id:\\d+}/cover")
	public void uploadCover(@PathVariable long id, @RequestPart MultipartFile file) {
		shopService.uploadCover(id, MultipartFileMapper.toUploadFile(file));
	}
	
	@GetMapping("{id:\\d+}")
	public ShopDTO getShop(@PathVariable long id) {
		return shopService.findById(id);
	}

	@GetMapping("{id:\\d+}/statistic")
	public ShopStatisticDTO getStatistic(@PathVariable long id) {
		return shopService.getShopStatistic(id);
	}

	@GetMapping("{id:\\d+}/setting")
	public ShopSettingDTO getSetting(@PathVariable long id) {
		return shopService.getShopSetting(id);
	}
	
	@GetMapping("{id:\\d+}/pending-order-count")
	public long getPendingOrderCount(@PathVariable long id) {
		return shopService.getPendingOrderCount(id);
	}

	@GetMapping("{id:\\d+}/monthly-sales")
	public List<ShopMonthlySaleDTO> getMonthlySale(@PathVariable long id, @RequestParam int year) {
		return shopService.getMonthlySale(id, year);
	}
	
	@GetMapping("{id:\\d+}/products")
	public PageDataDTO<ProductDTO> getProducts(
			@PathVariable long id, 
			@RequestParam(required = false) String q,
			@RequestParam(required = false, name = "brand") String[] brands,
			@RequestParam(required = false, name = "category-id") Integer categoryId,
			@RequestParam(required = false, name = "discount-id") Long discountId,
			@RequestParam(required = false) Product.Status status,
			@RequestParam(required = false) Integer page) {
		
		validateShopMemberUseCase.apply(id, authentication.getUserId());

		var query = ProductQuery.builder()
				.q(q)
				.categoryId(categoryId)
				.shopId(id)
				.discountId(discountId)
				.brands(brands)
				.status(status)
				.page(page).build();

		return productService.findAll(query);
	}

	@PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'SHOP_READ')")
    @GetMapping
    public PageDataDTO<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean expired,
            @RequestParam(required = false) Shop.Status status,
            @RequestParam(required = false) Integer page) {
    	
        var query = ShopQuery.builder()
                .q(q)
                .expired(expired)
                .status(status)
                .page(page)
                .build();
        return shopService.findAll(query);
    }
}
