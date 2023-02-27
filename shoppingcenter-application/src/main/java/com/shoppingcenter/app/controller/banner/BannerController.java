package com.shoppingcenter.app.controller.banner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcenter.app.controller.banner.dto.BannerDTO;
import com.shoppingcenter.app.controller.banner.dto.BannerEditDTO;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/banners")
@Tag(name = "Banner")
public class BannerController {

    @Autowired
    private BannerFacade bannerFacade;

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute BannerEditDTO banner) {
        bannerFacade.save(banner);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping
    public void update(@ModelAttribute BannerEditDTO banner) {
        bannerFacade.save(banner);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @PutMapping("{id:\\d+}/position")
    public void updatePosition(@PathVariable int id, @RequestParam int position) {
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable int id) {
        bannerFacade.delete(id);
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @GetMapping("{id:\\d+}")
    public BannerDTO getBanner(@PathVariable int id) {
        return bannerFacade.findById(id);
    }

    @GetMapping
    public List<BannerDTO> getBanners() {
        return bannerFacade.findAll();
    }

}
