package com.marketplace.api.admin.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.PageDataDTO;
import com.marketplace.api.vendor.shop.ShopMemberDTO;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.Shop.Status;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.marketplace.domain.shop.ShopQuery;

@RestController
@RequestMapping("api/v1/admin/shops")
@Tag(name = "Admin")
public class ShopController {

    @Autowired
    private ShopControllerFacade shopFacade;

    @PreAuthorize("hasPermission('SHOP', 'WRITE')")
    @PutMapping("{id:\\d+}/approve")
    public void approveShop(@PathVariable long id) {
    	shopFacade.updateStatus(id, Status.APPROVED);
    }
    
    @PreAuthorize("hasPermission('SHOP', 'WRITE')")
    @PutMapping("{id:\\d+}/disable")
    public void disableShop(@PathVariable long id) {
    	shopFacade.updateStatus(id, Status.DISABLED);
    }
    
    @PreAuthorize("hasPermission('SHOP', 'WRITE')")
    @PutMapping("{id:\\d+}/make-featured")
    public void makeFeatured(@PathVariable long id) {
    	shopFacade.updateFeatured(id, true);
    }
    
    @PreAuthorize("hasPermission('SHOP', 'WRITE')")
    @PutMapping("{id:\\d+}/remove-featured")
    public void removeFeatured(@PathVariable long id) {
    	shopFacade.updateFeatured(id, false);
    }
	
    @PreAuthorize("hasPermission('SHOP', 'READ') or hasPermission('SHOP', 'WRITE')")
	@GetMapping("{id:\\d+}")
	public ShopDTO getShop(@PathVariable long id) {
		return shopFacade.findById(id);
	}
    
    @PreAuthorize("hasPermission('SHOP', 'READ') or hasPermission('SHOP', 'WRITE')")
	@GetMapping("{id:\\d+}/members")
	public List<ShopMemberDTO> getShopMembers(@PathVariable long id) {
		return shopFacade.getShopMembers(id);
	}

	@PreAuthorize("hasPermission('SHOP', 'READ') or hasPermission('SHOP', 'WRITE')")
    @GetMapping
    public PageDataDTO<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean expired,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false, name = "city-id") Long cityId,
            @RequestParam(required = false) Shop.Status status,
            @RequestParam(required = false) Integer page) {
    	
        var query = ShopQuery.builder()
                .q(q)
                .expired(expired)
                .status(status)
                .cityId(cityId)
                .featured(featured)
                .page(page)
                .build();
        return shopFacade.findAll(query);
    }
}
