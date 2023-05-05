package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shop.dto.ShopAcceptedPaymentDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops/{shopId:\\d+}/accepted-payments")
@Tag(name = "ShopAcceptedPayment")
public class ShopAcceptedPaymentController {
	
	@Autowired
	private ShopAcceptedPaymentFacade shopAcceptedPaymentFacade;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
    public void create(@PathVariable long shopId, @RequestBody ShopAcceptedPaymentDTO payment) {
		payment.setShopId(shopId);
        shopAcceptedPaymentFacade.save(payment);
    }
	
	@PutMapping
    public void update(@PathVariable long shopId, @RequestBody ShopAcceptedPaymentDTO payment) {
		payment.setShopId(shopId);
        shopAcceptedPaymentFacade.save(payment);
    }
	
	@DeleteMapping("{paymentId:\\d+}")
    public void delete(@PathVariable long shopId, @PathVariable long paymentId) {
        shopAcceptedPaymentFacade.delete(paymentId);
    }
	
	@GetMapping
    public List<ShopAcceptedPaymentDTO> findAll(@PathVariable long shopId) {
        return shopAcceptedPaymentFacade.findAllByShop(shopId);
    }

}
