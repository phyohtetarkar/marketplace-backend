package com.marketplace.app;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.aop.SpringProxy;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.core.DecoratingProxy;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.projection.TargetAware;
import org.springframework.util.ReflectionUtils;

import com.marketplace.api.Authz;
import com.marketplace.data.banner.view.BannerImageView;
import com.marketplace.data.category.CategoryNameEntity;
import com.marketplace.data.category.view.CategoryImageView;
import com.marketplace.data.category.view.CategoryWithNameView;
import com.marketplace.data.general.view.SiteSettingAboutUsView;
import com.marketplace.data.general.view.SiteSettingAssetsView;
import com.marketplace.data.general.view.SiteSettingPrivacyPolicyView;
import com.marketplace.data.general.view.SiteSettingTermsAndConditionsView;
import com.marketplace.data.product.FavoriteProductEntity;
import com.marketplace.data.product.ProductAttributeEntity;
import com.marketplace.data.product.ProductVariantAttributeEntity;
import com.marketplace.data.product.view.ProductBrandView;
import com.marketplace.data.product.view.ProductImageNameView;
import com.marketplace.data.product.view.ProductStatusView;
import com.marketplace.data.review.ShopReviewEntity;
import com.marketplace.data.shop.ShopMemberEntity;
import com.marketplace.data.shop.ShopMonthlySaleEntity;
import com.marketplace.data.shop.view.ShopCoverView;
import com.marketplace.data.shop.view.ShopExpiredAtView;
import com.marketplace.data.shop.view.ShopLogoView;
import com.marketplace.data.shop.view.ShopStatusView;
import com.marketplace.data.shoppingcart.CartItemEntity;
import com.marketplace.data.subscription.ShopSubscriptionTimeEntity;
import com.marketplace.data.user.UserPermissionEntity;
import com.marketplace.data.user.view.UserImageView;
import com.marketplace.data.user.view.UserRoleView;

public class NativeRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		var hashCode = "hashCode";
		var equals = "equals";
		
		var methods = new ArrayList<Method>();
		
		methods.add(ReflectionUtils.findMethod(UserPermissionEntity.ID.class, hashCode));
		methods.add(ReflectionUtils.findMethod(UserPermissionEntity.ID.class, equals, Object.class));
		
        methods.add(ReflectionUtils.findMethod(ProductVariantAttributeEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ProductVariantAttributeEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(CategoryNameEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(CategoryNameEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(ShopSubscriptionTimeEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ShopSubscriptionTimeEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(ShopMonthlySaleEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ShopMonthlySaleEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(ShopMemberEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ShopMemberEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(FavoriteProductEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(FavoriteProductEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(ProductAttributeEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ProductAttributeEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(ShopReviewEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(ShopReviewEntity.ID.class, equals, Object.class));
        
        methods.add(ReflectionUtils.findMethod(CartItemEntity.ID.class, hashCode));
        methods.add(ReflectionUtils.findMethod(CartItemEntity.ID.class, equals, Object.class));
		
        for (var method : methods) {
        	hints.reflection().registerMethod(method, ExecutableMode.INVOKE);
        }
        
        var isShopMember = ReflectionUtils.findMethod(Authz.class, "isShopMember", Long.class);
        hints.reflection().registerMethod(isShopMember, ExecutableMode.INVOKE);
        
        var setAuditorAware = ReflectionUtils.findMethod(AuditingHandler.class, "setAuditorAware", AuditorAware.class);
        hints.reflection().registerMethod(setAuditorAware, ExecutableMode.INVOKE);
        
        Function<Class<?>, Class<?>[]> projectionProxies = clazz -> {
        	return new Class<?>[] { clazz, TargetAware.class, SpringProxy.class, DecoratingProxy.class };
        };
        
        hints.proxies().registerJdkProxy(projectionProxies.apply(BannerImageView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(CategoryImageView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(CategoryWithNameView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(SiteSettingAboutUsView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(SiteSettingAssetsView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(SiteSettingTermsAndConditionsView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(SiteSettingPrivacyPolicyView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ProductImageNameView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ProductStatusView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ProductBrandView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ShopCoverView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ShopLogoView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ShopExpiredAtView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(ShopStatusView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(UserImageView.class));
        hints.proxies().registerJdkProxy(projectionProxies.apply(UserRoleView.class));
        
	}

}
