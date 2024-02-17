package com.marketplace.app.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.marketplace.app.common.AppProperties;
import com.marketplace.app.security.CustomJwtAuthenticationConverter;
import com.marketplace.app.security.CustomPermissionEvaluator;
import com.marketplace.app.security.FirebaseUserAdapter;
import com.marketplace.app.security.Http401UnauthorizedEntryPoint;
import com.marketplace.domain.shop.dao.ShopMemberDao;
import com.marketplace.domain.user.dao.UserDao;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	// private static final String COGNITO_GROUPS = "cognito:groups";

	@Bean
	static MethodSecurityExpressionHandler methodSecurityExpressionHandler(ShopMemberDao shopMemberDao, UserDao userDao) {
		var handler = new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(new CustomPermissionEvaluator(userDao));
		return handler;
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain publicApiFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(c -> c.disable())
				.sessionManagement(sm -> {
					sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.securityMatchers(mathers -> {
					mathers
						.requestMatchers(HttpMethod.GET, "/api/**/content/**")
						.requestMatchers(HttpMethod.POST, "/api/**/payment/notify");
				});

		return http.build();
	}

	@Bean
	@Order(2)
	SecurityFilterChain privateApiFilterChain(
			HttpSecurity http, 
			UserDao userDao,
			FirebaseUserAdapter firebaseUserAdapter) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(c -> c.disable())
				.sessionManagement(sm -> {
					sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.securityMatcher("/api/**")
				.authorizeHttpRequests(authz -> {
					authz
						.requestMatchers("/api/v*/admin/**").hasAnyAuthority("ROLE_OWNER", "ROLE_ADMIN")
						.anyRequest().authenticated();
				})
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint(new Http401UnauthorizedEntryPoint());
				})
				.oauth2ResourceServer(oauth2 -> {
					oauth2
						.jwt(jwt -> {
							jwt.jwtAuthenticationConverter(new CustomJwtAuthenticationConverter(userDao, firebaseUserAdapter));
						})
						.authenticationEntryPoint(new Http401UnauthorizedEntryPoint());
				});

		return http.build();
	}

//	@Bean
//	WebSecurityCustomizer webSecurityCustomizer() {
////		 String[] excludes = {
////			 "/api/v*/auth/sign-in",
////			 "/api/v*/auth/sign-up",
////			 "/api/v*/auth/reset-password",
////			 "/api/v*/auth/refresh"
////		 };
//
//		String[] excludes = {};
//
//		return (web) -> web.ignoring();
//	}

	@Bean
	CorsConfigurationSource corsConfigurationSource(Environment env, AppProperties props) {
		var allowedOrigins = new ArrayList<String>();
		
		allowedOrigins.addAll(props.getCorsOrigins());
		
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	

}
