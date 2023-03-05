package com.shoppingcenter.app.controller.shop;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
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

import com.shoppingcenter.app.controller.product.ProductFacade;
import com.shoppingcenter.app.controller.product.dto.ProductDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopContactDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopEditDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopGeneralDTO;
import com.shoppingcenter.app.controller.shop.dto.ShopInsightsDTO;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.PageData;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.product.Product;
import com.shoppingcenter.domain.product.ProductQuery;
import com.shoppingcenter.domain.shop.Shop.Status;
import com.shoppingcenter.domain.shop.ShopQuery;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/shops")
@Tag(name = "Shop")
public class ShopController {

    @Autowired
    private ShopFacade shopFacade;

    @Autowired
    private ProductFacade productFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@ModelAttribute ShopEditDTO shop) {
        shopFacade.create(shop);
    }

    @PutMapping("{id:\\d+}/general")
    public ShopDTO updateGeneralInfo(@PathVariable long id, @RequestBody ShopGeneralDTO general) {
        return shopFacade.updateGeneralInfo(general);
    }

    @PutMapping("{id:\\d+}/contact")
    public void updateContact(@PathVariable long id, @RequestBody ShopContactDTO contact) {
        shopFacade.updateContact(contact);
    }

    @PostMapping(value = "{id:\\d+}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadLogo(@PathVariable long id, @RequestPart MultipartFile file, Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return;
            }

            var uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            shopFacade.uploadLogo(id, uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @PostMapping(value = "{id:\\d+}/cover", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadCover(@PathVariable long id, @RequestPart MultipartFile file, Authentication authentication) {
        try {
            if (file.isEmpty()) {
                return;
            }

            var uploadFile = new UploadFile();
            uploadFile.setInputStream(file.getInputStream());
            uploadFile.setOriginalFileName(file.getOriginalFilename());
            uploadFile.setSize(file.getSize());
            shopFacade.uploadCover(id, uploadFile);
        } catch (IOException e) {
            throw new ApplicationException("Failed to upload image");
        }
    }

    @GetMapping("{id:\\d+}/insights")
    public ShopInsightsDTO getInsights(@PathVariable long id) {
        return shopFacade.getShopInsights(id);
    }

    @GetMapping("{slug}")
    public ShopDTO findBySlug(@PathVariable String slug) {
        return shopFacade.findBySlug(slug);
    }

    @GetMapping
    public PageData<ShopDTO> findAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page) {
        var query = ShopQuery.builder()
                .q(q)
                .status(Status.ACTIVE)
                .page(page)
                .build();
        return shopFacade.findAll(query);
    }

    @GetMapping("{id:\\d+}/products")
    public PageData<ProductDTO> findProducts(
            @PathVariable long id,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Product.Status status,
            @RequestParam(required = false, name = "brand") String[] brands,
            @RequestParam(required = false, name = "category-slug") String categorySlug,
            @RequestParam(required = false, name = "discount-id") Long discountId,
            @RequestParam(required = false, name = "max-price") Double maxPrice,
            @RequestParam(required = false) Integer page,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access denied");
        }
        var query = ProductQuery.builder()
                .q(q)
                .categorySlug(categorySlug)
                .shopId(id)
                .discountId(discountId)
                .maxPrice(maxPrice)
                .status(status)
                .brands(brands)
                .page(page)
                .build();

        return productFacade.findAll(query);
    }

}
