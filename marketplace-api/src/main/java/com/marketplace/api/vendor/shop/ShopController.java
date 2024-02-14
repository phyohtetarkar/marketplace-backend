package com.marketplace.api.vendor.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/vendor/shops/{shopId:\\d+}")
@PreAuthorize("@authz.isShopMember(#shopId)")
@Tag(name = "Vendor")
public class ShopController {

	@Autowired
	private ShopControllerFacade shopFacade;

	@PutMapping
	public ShopDTO update(@PathVariable long shopId, @RequestBody ShopUpdateDTO body) {
		body.setShopId(shopId);
		return shopFacade.update(body);
	}

	@PutMapping("contact")
	public void updateContact(@PathVariable long shopId, @RequestBody ShopContactUpdateDTO body) {
		body.setShopId(shopId);
		shopFacade.updateContact(body);
	}

	@PutMapping("setting")
	public void updateSetting(@PathVariable long shopId, @RequestBody ShopSettingDTO body) {
		body.setShopId(shopId);
		shopFacade.updateSetting(body);
	}

	@PutMapping("logo")
	public void uploadLogo(@PathVariable long shopId, @RequestPart MultipartFile file) {
		shopFacade.uploadLogo(shopId, file);
	}

	@PutMapping("cover")
	public void uploadCover(@PathVariable long shopId, @RequestPart MultipartFile file) {
		shopFacade.uploadCover(shopId, file);
	}

	@PostMapping("accepted-payments")
	public void saveAcceptedPayment(@PathVariable long shopId, @RequestBody ShopAcceptedPaymentDTO body) {
		shopFacade.saveAcceptedPayment(shopId, body);
	}

	@DeleteMapping("accepted-payments/{id:\\d+}")
	public void deleteAcceptedPayment(@PathVariable long shopId, @PathVariable long id) {
		shopFacade.deleteAcceptedPayment(id);
	}

	@GetMapping
	public ShopDTO getShop(@PathVariable long shopId) {
		return shopFacade.findById(shopId);
	}

	@GetMapping("statistic")
	public ShopStatisticDTO getStatistic(@PathVariable long shopId) {
		return shopFacade.getShopStatistic(shopId);
	}

	@GetMapping("setting")
	public ShopSettingDTO getSetting(@PathVariable long shopId) {
		return shopFacade.getShopSetting(shopId);
	}

	@GetMapping("pending-order-count")
	public long getPendingOrderCount(@PathVariable long shopId) {
		return shopFacade.getPendingOrderCount(shopId);
	}

	@GetMapping("monthly-sales")
	public List<ShopMonthlySaleDTO> getMonthlySale(
			@PathVariable long shopId, 
			@RequestParam int year) {
		return shopFacade.getMonthlySale(shopId, year);
	}
}
