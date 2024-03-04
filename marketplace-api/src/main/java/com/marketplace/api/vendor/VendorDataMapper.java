package com.marketplace.api.vendor;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marketplace.api.CommonDataMapper;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.vendor.discount.DiscountDTO;
import com.marketplace.api.vendor.discount.DiscountEditDTO;
import com.marketplace.api.vendor.product.ProductCreateDTO;
import com.marketplace.api.vendor.product.ProductDTO;
import com.marketplace.api.vendor.product.ProductUpdateDTO;
import com.marketplace.api.vendor.shop.ShopAcceptedPaymentDTO;
import com.marketplace.api.vendor.shop.ShopContactDTO;
import com.marketplace.api.vendor.shop.ShopContactUpdateDTO;
import com.marketplace.api.vendor.shop.ShopCreateDTO;
import com.marketplace.api.vendor.shop.ShopDTO;
import com.marketplace.api.vendor.shop.ShopMemberDTO;
import com.marketplace.api.vendor.shop.ShopMonthlySaleDTO;
import com.marketplace.api.vendor.shop.ShopSettingDTO;
import com.marketplace.api.vendor.shop.ShopStatisticDTO;
import com.marketplace.api.vendor.shop.ShopUpdateDTO;
import com.marketplace.api.vendor.subscription.RenewSubscriptionDTO;
import com.marketplace.api.vendor.subscription.ShopSubscriptionDTO;
import com.marketplace.api.vendor.subscription.SubscriptionPromoDTO;
import com.marketplace.domain.PageData;
import com.marketplace.domain.discount.Discount;
import com.marketplace.domain.discount.DiscountInput;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductCreateInput;
import com.marketplace.domain.product.ProductUpdateInput;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopAcceptedPaymentInput;
import com.marketplace.domain.shop.ShopContact;
import com.marketplace.domain.shop.ShopContactInput;
import com.marketplace.domain.shop.ShopCreateInput;
import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.shop.ShopMonthlySale;
import com.marketplace.domain.shop.ShopSetting;
import com.marketplace.domain.shop.ShopSettingInput;
import com.marketplace.domain.shop.ShopStatistic;
import com.marketplace.domain.shop.ShopUpdateInput;
import com.marketplace.domain.subscription.RenewShopSubscriptionInput;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.SubscriptionPromo;

@Mapper(componentModel = "spring")
public interface VendorDataMapper extends CommonDataMapper {

	DiscountDTO map(Discount source);
	
	List<DiscountDTO> mapDiscountList(List<Discount> source);
	
	DiscountInput map(DiscountEditDTO source);
	
	ProductDTO map(Product source);
	
	PageDataDTO<ProductDTO> mapProductPage(PageData<Product> source);
	
	@Mapping(target = "name", ignore = true)
	ProductCreateInput.Image map(ProductCreateDTO.Image source);
	
	ProductCreateInput.Variant map(ProductCreateDTO.Variant source);
	
	@Mapping(target = "thumbnail", ignore = true)
	ProductCreateInput map(ProductCreateDTO source);
	
	ProductUpdateInput map(ProductUpdateDTO source);
	
	List<ProductCreateInput.Image> mapProductImageEditList(List<ProductCreateDTO.Image> source);
	
	List<ProductCreateInput.Variant> mapProductVariantEditList(List<ProductCreateDTO.Variant> source);
	
	ShopDTO map(Shop source);
	
	ShopContactDTO map(ShopContact source);
	
	ShopMemberDTO map(ShopMember source);
	
	ShopMonthlySaleDTO map(ShopMonthlySale source);
	
	List<ShopMonthlySaleDTO> mapShopMonthlySaleList(List<ShopMonthlySale> source);
	
	@Mapping(target = "shopId", ignore = true)
	ShopSettingDTO map(ShopSetting source);
	
	ShopStatisticDTO map(ShopStatistic source);
	
	ShopCreateInput map(ShopCreateDTO source);
	
	ShopUpdateInput map(ShopUpdateDTO source);
	
	ShopContactInput map(ShopContactUpdateDTO source);
	
	ShopSettingInput map(ShopSettingDTO source);
	
	@Mapping(target = "shopId", ignore = true)
	ShopAcceptedPaymentInput map(ShopAcceptedPaymentDTO source);
	
	ShopSubscriptionDTO map(ShopSubscription source);
	
	List<ShopSubscriptionDTO> mapShopSubscriptionList(List<ShopSubscription> source);
	
	SubscriptionPromoDTO map(SubscriptionPromo source);
	
	RenewShopSubscriptionInput map(RenewSubscriptionDTO source);

}
