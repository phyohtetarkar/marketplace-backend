package com.shoppingcenter.app.controller.shop;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
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

import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopEditDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.service.ApplicationException;
import com.shoppingcenter.service.PageData;
import com.shoppingcenter.service.UploadFile;
import com.shoppingcenter.service.shop.ShopQuery;
import com.shoppingcenter.service.shop.ShopQueryService;
import com.shoppingcenter.service.shop.ShopService;
import com.shoppingcenter.service.shop.model.Shop;
import com.shoppingcenter.service.shop.model.ShopContact;
import com.shoppingcenter.service.shop.model.ShopGeneral;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops")
@Tag(name = "Shop")
public class ShopController {

    @Autowired
    private ShopService service;

    @Autowired
    private ShopQueryService shopQueryService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute ShopEditDTO shop) {
        service.create(modelMapper.map(shop, Shop.class));
    }

    @PutMapping("{id:\\d+}/general")
    public ShopDTO updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneralDTO general) {
        Shop shop = service.updateGeneralInfo(modelMapper.map(general, ShopGeneral.class));
        return modelMapper.map(shop, ShopDTO.class);
    }

    @PutMapping("{id:\\d+}/contact")
    public void updateContact(@PathVariable long id, @RequestBody ShopContactDTO contact) {
        service.updateContact(modelMapper.map(contact, ShopContact.class));
    }

    @PostMapping(value = "{id:\\d+}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadLogo(@PathVariable long id, @RequestPart MultipartFile file, Authentication authentication) {
        try {
            // TODO: check permission
            if (file.isEmpty()) {
                return;
            }

            UploadFile uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            service.uploadLogo(id, uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @PostMapping(value = "{id:\\d+}/cover", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadCover(@PathVariable long id, @RequestPart MultipartFile file, Authentication authentication) {
        try {
            // TODO: check permission
            if (file.isEmpty()) {
                return;
            }

            UploadFile uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            service.uploadCover(id, uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @GetMapping("{slug}")
    public ShopDTO findBySlug(@PathVariable String slug) {
        return modelMapper.map(shopQueryService.findBySlug(slug), ShopDTO.class);
    }

    @GetMapping("{slug}/exists")
    public boolean existsBySlug(@PathVariable String slug, @RequestParam("exclude") Long excludeId) {
        return shopQueryService.existsBySlug(slug, excludeId);
    }

    // @GetMapping("{slug}/has-permission")
    // public boolean checkPermisson(@PathVariable String slug, @RequestParam String
    // permission, Authentication authentication) {
    // if (permission == "create-product") {
    // return shopQueryService.canCreateProduct(slug, authentication.getName());
    // }
    // return false;
    // }

    // @GetMapping("{id:\\d+}/products")
    // public PageData<ProductDTO> findProductsByShop(
    // @PathVariable long id,
    // @RequestParam(required = false) String q,
    // @RequestParam(required = false) Integer page) {
    // ProductQuery query = ProductQuery.builder()
    // .q(q)
    // .shopId(id)
    // .page(page)
    // .build();
    // return modelMapper.map(productQueryService.findAll(query),
    // ProductDTO.pageType());
    // }

    @GetMapping("hints")
    public List<ShopDTO> searchHints(@RequestParam String q) {
        return modelMapper.map(shopQueryService.getHints(q), ShopDTO.listType());
    }

    @GetMapping("me")
    public PageData<ShopDTO> getMyShops(
            @RequestParam(required = false) Integer page,
            Authentication authentication) {
        return modelMapper.map(shopQueryService.findByUser(authentication.getName(), page), ShopDTO.pageType());
    }

    @Secured({ "ROLE_ADMIN", "ROLE_OWNER" })
    @GetMapping("denied")
    public PageData<ShopDTO> findDeniedShops(@RequestParam(required = false) Integer page) {
        return modelMapper.map(shopQueryService.findDenied(page), ShopDTO.pageType());
    }

    @GetMapping
    public PageData<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page) {
        ShopQuery query = ShopQuery.builder()
                .q(q)
                .page(page)
                .build();
        return modelMapper.map(shopQueryService.findAll(query), ShopDTO.pageType());
    }

}
