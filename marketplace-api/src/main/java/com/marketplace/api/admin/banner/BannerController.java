package com.marketplace.api.admin.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.api.consumer.banner.BannerDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/admin/banners")
@Tag(name = "Admin")
public class BannerController {

	@Autowired
	private BannerControllerFacade bannerFacade;
	
	@PreAuthorize("hasPermission('BANNER', 'WRITE')")
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute BannerEditDTO body) {
        bannerFacade.save(body);
    }
    
	@PreAuthorize("hasPermission('BANNER', 'WRITE')")
    @PutMapping
    public void update(@ModelAttribute BannerEditDTO body) {
        bannerFacade.save(body);
    }
    
	@PreAuthorize("hasPermission('BANNER', 'WRITE')")
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        bannerFacade.delete(id);
    }

	@PreAuthorize("hasPermission('BANNER', 'READ') or hasPermission('BANNER', 'WRITE')")
    @GetMapping("{id:\\d+}")
    public BannerDTO getBanner(@PathVariable int id) {
        return bannerFacade.findById(id);
    }
    
	@PreAuthorize("hasPermission('BANNER', 'READ') or hasPermission('BANNER', 'WRITE')")
    @GetMapping
    public List<BannerDTO> getBanners() {
        return bannerFacade.findAll();
    }
	
}
