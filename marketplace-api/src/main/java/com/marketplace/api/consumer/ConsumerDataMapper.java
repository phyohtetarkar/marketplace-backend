package com.marketplace.api.consumer;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marketplace.api.CommonDataMapper;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.consumer.banner.BannerDTO;
import com.marketplace.api.consumer.category.CategoryDTO;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.api.consumer.general.HomeDataDTO;
import com.marketplace.api.consumer.general.SiteAssetsDTO;
import com.marketplace.api.consumer.order.OrderCreateDTO;
import com.marketplace.api.consumer.order.OrderDTO;
import com.marketplace.api.consumer.product.ProductDTO;
import com.marketplace.api.consumer.product.ProductFilterDTO;
import com.marketplace.api.consumer.review.ShopReviewDTO;
import com.marketplace.api.consumer.review.ShopReviewEditDTO;
import com.marketplace.api.consumer.shop.ShopContactDTO;
import com.marketplace.api.consumer.shop.ShopDTO;
import com.marketplace.api.consumer.shop.ShopSettingDTO;
import com.marketplace.api.consumer.shoppingcart.CartItemDTO;
import com.marketplace.api.consumer.shoppingcart.CartItemEditDTO;
import com.marketplace.api.consumer.subscription.SubscriptionPlanDTO;
import com.marketplace.api.consumer.user.ProfileStatisticDTO;
import com.marketplace.api.consumer.user.UserDTO;
import com.marketplace.api.consumer.user.UserEditDTO;
import com.marketplace.api.vendor.shop.ShopAcceptedPaymentDTO;
import com.marketplace.domain.PageData;
import com.marketplace.domain.banner.Banner;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.general.City;
import com.marketplace.domain.general.HomeData;
import com.marketplace.domain.general.SiteSettingAssets;
import com.marketplace.domain.order.Order;
import com.marketplace.domain.order.OrderCreateInput;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductFilter;
import com.marketplace.domain.review.ShopReview;
import com.marketplace.domain.review.ShopReviewInput;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopAcceptedPayment;
import com.marketplace.domain.shop.ShopContact;
import com.marketplace.domain.shop.ShopSetting;
import com.marketplace.domain.shoppingcart.CartItem;
import com.marketplace.domain.shoppingcart.CartItemInput;
import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.user.ProfileStatistic;
import com.marketplace.domain.user.ProfileUpdateInput;
import com.marketplace.domain.user.User;

@Mapper(componentModel = "spring")
public interface ConsumerDataMapper extends CommonDataMapper {

	BannerDTO map(Banner source);

	List<BannerDTO> mapBannerList(List<Banner> source);

	CategoryDTO map(Category source);

	List<CategoryDTO> mapCategoryList(List<Category> source);
	
	CityDTO map(City source);
	
	List<CityDTO> mapCityList(List<City> source);
	
	HomeDataDTO map(HomeData source);
	
	SiteAssetsDTO map(SiteSettingAssets source);

	ProductDTO map(Product source);
	
	ProductFilterDTO map(ProductFilter source);
	
	List<ProductDTO> mapProductList(List<Product> source);
	
	PageDataDTO<ProductDTO> mapProductPage(PageData<Product> source);
	
	ShopDTO map(Shop source);
	
	ShopSettingDTO map(ShopSetting source);
	
	ShopAcceptedPaymentDTO map(ShopAcceptedPayment source);
	
	ShopContactDTO map(ShopContact source);
	
	PageDataDTO<ShopDTO> mapShopPage(PageData<Shop> source);
	
	List<ShopAcceptedPaymentDTO> mapShopAcceptedPaymentList(List<ShopAcceptedPayment> source);
	
	ShopReviewDTO map(ShopReview source);
	
	PageDataDTO<ShopReviewDTO> mapShopReviewPage(PageData<ShopReview> source);
	
	CartItemDTO map(CartItem source);
	
	List<CartItemDTO> mapCartItemList(List<CartItem> source);
	
	SubscriptionPlanDTO map(SubscriptionPlan source);
	
	List<SubscriptionPlanDTO> mapSubscriptionPlanList(List<SubscriptionPlan> source);
	
	ProfileStatisticDTO map(ProfileStatistic source);
	
	UserDTO map(User user);
	
	@Mapping(target = "payment.file", ignore = true)
	OrderDTO map(Order order);
	
	PageDataDTO<OrderDTO> mapOrderPage(PageData<Order> source);
	
	CartItemInput map(CartItemEditDTO source);
	
	ShopReviewInput map(ShopReviewEditDTO source);
	
	OrderCreateInput map(OrderCreateDTO source);
	
	ProfileUpdateInput map(UserEditDTO source);
	
}
