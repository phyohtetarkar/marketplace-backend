package com.marketplace.api.admin;

import java.util.List;

import org.mapstruct.Mapper;

import com.marketplace.api.CommonDataMapper;
import com.marketplace.api.PageDataDTO;
import com.marketplace.api.admin.banner.BannerEditDTO;
import com.marketplace.api.admin.category.CategoryDTO;
import com.marketplace.api.admin.category.CategoryEditDTO;
import com.marketplace.api.admin.general.DashboardDataDTO;
import com.marketplace.api.admin.general.SiteSettingDTO;
import com.marketplace.api.admin.shop.ShopDTO;
import com.marketplace.api.admin.subscription.ShopSubscriptionDTO;
import com.marketplace.api.admin.subscription.ShopSubscriptionTransactionDTO;
import com.marketplace.api.admin.subscription.SubscriptionPlanDTO;
import com.marketplace.api.admin.subscription.SubscriptionPlanEditDTO;
import com.marketplace.api.admin.subscription.SubscriptionPromoDTO;
import com.marketplace.api.admin.subscription.SubscriptionPromoEditDTO;
import com.marketplace.api.admin.user.UserDTO;
import com.marketplace.api.consumer.general.CityDTO;
import com.marketplace.api.vendor.shop.ShopMemberDTO;
import com.marketplace.domain.PageData;
import com.marketplace.domain.banner.BannerInput;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryInput;
import com.marketplace.domain.general.City;
import com.marketplace.domain.general.DashboardData;
import com.marketplace.domain.general.SiteSetting;
import com.marketplace.domain.shop.Shop;
import com.marketplace.domain.shop.ShopMember;
import com.marketplace.domain.subscription.ShopSubscription;
import com.marketplace.domain.subscription.ShopSubscriptionTransaction;
import com.marketplace.domain.subscription.SubscriptionPlan;
import com.marketplace.domain.subscription.SubscriptionPlanInput;
import com.marketplace.domain.subscription.SubscriptionPromo;
import com.marketplace.domain.subscription.SubscriptionPromoInput;
import com.marketplace.domain.user.User;

@Mapper(componentModel = "spring")
public interface AdminDataMapper extends CommonDataMapper {
	
	BannerInput map(BannerEditDTO source);

	CategoryDTO map(Category source);
	
	List<CategoryDTO> mapCategoryList(List<Category> source);
	
	PageDataDTO<CategoryDTO> mapCategoryPage(PageData<Category> source);
	
	CategoryInput map(CategoryEditDTO source);
	
	City map(CityDTO source);
	
	DashboardDataDTO map(DashboardData source);
	
	SiteSettingDTO map(SiteSetting source);
	
	ShopDTO map(Shop source);
	
	PageDataDTO<ShopDTO> mapShopPage(PageData<Shop> source);
	
	List<ShopMemberDTO> mapShopMemberList(List<ShopMember> source);
	
	ShopSubscriptionDTO map(ShopSubscription source);
	
	ShopSubscriptionTransactionDTO map(ShopSubscriptionTransaction source);
	
	List<ShopSubscriptionDTO> map(List<ShopSubscription> source);
	
	PageDataDTO<ShopSubscriptionDTO> mapShopSubscriptionPage(PageData<ShopSubscription> source);
	
	SubscriptionPlanDTO map(SubscriptionPlan source);
	
	List<SubscriptionPlanDTO> mapSubscriptionPlanList(List<SubscriptionPlan> source);
	
	SubscriptionPlanInput map(SubscriptionPlanEditDTO source);
	
	SubscriptionPromoDTO map(SubscriptionPromo source);
	
	PageDataDTO<SubscriptionPromoDTO> mapSubscriptionPromoPage(PageData<SubscriptionPromo> source);
	
	SubscriptionPromoInput map(SubscriptionPromoEditDTO source);
	
	UserDTO map(User source);
	
	PageDataDTO<UserDTO> mapUserPage(PageData<User> source);
	
}
