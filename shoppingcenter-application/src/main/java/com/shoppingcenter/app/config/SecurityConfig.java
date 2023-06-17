package com.shoppingcenter.app.config;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shoppingcenter.app.common.AppProperties;
import com.shoppingcenter.app.security.CustomPermissionEvaluator;
import com.shoppingcenter.app.security.DefaultUserDetailsService;
import com.shoppingcenter.app.security.Http401UnauthorizedEntryPoint;
import com.shoppingcenter.app.security.JwtTokenFilter;
import com.shoppingcenter.app.security.JwtTokenUtil;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	// private static final String COGNITO_GROUPS = "cognito:groups";

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	Key key(AppProperties properties) {
		var secret = properties.getJwtSecret();
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new DefaultUserDetailsService();
	}

	@Bean
	static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(new CustomPermissionEvaluator());
		return handler;
	}

	
//	public JwtAuthenticationConverter jwtAuthenticationConverter() {
//		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//
//		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//			// var user = userRepo.findById(jwt.getSubject()).orElse(null);
//			var role = "ANONYMOUS";
//			// System.out.println(jwt.getSubject());
//			// if (user != null && !user.isDisabled()) {
//
//			// role = user.getRole();
//
//			// }
//			// System.out.println("Sub: " + jwt.getSubject());
//			// return jwt.getClaimAsStringList(COGNITO_GROUPS).stream().map(group -> {
//			// // String role = StringUtils.hasText(group) ? group : "USER";
//			// return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
//			// }).collect(Collectors.toList());
//
//			return AuthorityUtils.createAuthorityList("ROLE_" + role.toUpperCase());
//		});
//
//		return converter;
//	}

	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain clientApiFilterChain(
			HttpSecurity http, 
			JwtTokenUtil jwtTokenUtil,
			UserDetailsService userDetailsService, 
			CorsConfigurationSource corsConfigurationSource) throws Exception {
		// var tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		// var delegate = new XorCsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		// delegate.setCsrfRequestAttributeName("_csrf");
		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
		// default implementation of resolveCsrfTokenValue() from
		// CsrfTokenRequestHandler
		// CsrfTokenRequestHandler requestHandler = delegate::handle;
		http
				.cors().configurationSource(corsConfigurationSource).and()
				.csrf().disable()
				// .csrf((csrf) -> csrf
				// .csrfTokenRepository(tokenRepository)
				// .csrfTokenRequestHandler(requestHandler))
				.addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService),
						UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.securityMatcher("/api/**")
				.authorizeHttpRequests(authz -> {
					authz
							//.requestMatchers("/api/**/admin/**").hasAnyRole("ADMIN", "OWNER")
							.requestMatchers("/api/v*/auth/sign-in").permitAll()
							.requestMatchers("/api/v*/auth/sign-up").permitAll()
							.requestMatchers("/api/v*/auth/reset-password").permitAll()
							.requestMatchers("/api/v*/auth/refresh").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/products/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/banners/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/categories/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/shops/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/shop-reviews*").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/accepted-payments*").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/delivery-cities*").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/home").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/otp/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/search/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/subscription-plans").permitAll()
							.requestMatchers(HttpMethod.POST, "/api/v*/payment/notify").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/payment/*").permitAll()
							.anyRequest().authenticated();
				})
				.exceptionHandling()
				.authenticationEntryPoint(new Http401UnauthorizedEntryPoint());
		
		// .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// String[] excludes = {
		// "/api/**/sign-in",
		// "/api/**/sign-up",
		// "/api/**/reset-password",
		// "/api/**/csrf",
		// "/api/**/send-otp",
		// "/api/**/refresh"
		// };

		String[] excludes = {};

		return (web) -> web.ignoring().requestMatchers(excludes);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource(Environment env) {
		var allowedOrigins = new ArrayList<String>();
		if (env.acceptsProfiles(Profiles.of("prod"))) {
			allowedOrigins.add("https://shoppingcenter.com");
			allowedOrigins.add("https://admin.shoppingcenter.com");
		} else if (env.acceptsProfiles(Profiles.of("staging"))) {
			allowedOrigins.add("http://staging.shoppingmallmyanmar.com");
			allowedOrigins.add("http://staging-admin.shoppingmallmyanmar.com");
		} else {
			allowedOrigins.add("http://localhost:3000");
			allowedOrigins.add("http://localhost:3080");
			
		}
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
