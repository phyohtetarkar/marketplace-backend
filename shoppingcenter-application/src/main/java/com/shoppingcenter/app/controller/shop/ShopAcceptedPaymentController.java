package com.shoppingcenter.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.shop.dto.ShopAcceptedPaymentDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops/{shopId:\\d+}")
@Tag(name = "ShopAcceptedPayment")
public class ShopAcceptedPaymentController {
	
	@Autowired
	private ShopAcceptedPaymentFacade shopAcceptedPaymentFacade;
	
	@PostMapping("accepted-payments")
    public void create(@PathVariable long shopId, @RequestBody ShopAcceptedPaymentDTO payment) {
        shopAcceptedPaymentFacade.save(payment);
    }
	
	@PutMapping("accepted-payments")
    public void update(@PathVariable long shopId, @RequestBody ShopAcceptedPaymentDTO payment) {
        shopAcceptedPaymentFacade.save(payment);
    }
	
	@DeleteMapping("accepted-payments/{paymentId:\\d+}")
    public void update(@PathVariable long shopId, @PathVariable long paymentId) {
        shopAcceptedPaymentFacade.delete(paymentId);
    }
	
	@GetMapping("accepted-payments")
    public List<ShopAcceptedPaymentDTO> findAll(@PathVariable long shopId) {
        return shopAcceptedPaymentFacade.findAllByShop(shopId);
    }

}
