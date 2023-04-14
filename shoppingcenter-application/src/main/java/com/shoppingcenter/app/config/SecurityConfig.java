package com.shoppingcenter.app.config;

import java.security.Key;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shoppingcenter.app.security.CustomPermissionEvaluator;
import com.shoppingcenter.app.security.DefaultUserDetailsService;
import com.shoppingcenter.app.security.Http401UnauthorizedEntryPoint;
import com.shoppingcenter.app.security.JwtTokenFilter;
import com.shoppingcenter.app.security.JwtTokenUtil;
import com.shoppingcenter.domain.common.AppProperties;

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

	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			// var user = userRepo.findById(jwt.getSubject()).orElse(null);
			var role = "ANONYMOUS";
			// System.out.println(jwt.getSubject());
			// if (user != null && !user.isDisabled()) {

			// role = user.getRole();

			// }
			// System.out.println("Sub: " + jwt.getSubject());
			// return jwt.getClaimAsStringList(COGNITO_GROUPS).stream().map(group -> {
			// // String role = StringUtils.hasText(group) ? group : "USER";
			// return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
			// }).collect(Collectors.toList());

			return AuthorityUtils.createAuthorityList("ROLE_" + role.toUpperCase());
		});

		return converter;
	}

	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

	@Bean
	SecurityFilterChain clientApiFilterChain(HttpSecurity http, JwtTokenUtil jwtTokenUtil,
			UserDetailsService userDetailsService) throws Exception {
		http
				.cors().and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService),
						UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.securityMatcher("/api/**")
				.authorizeHttpRequests(authz -> {
					authz
							// .antMatchers("/api/**/sign-in", "/api/**/sign-up", "/api/**/social-sign-in",
							// "/api/**/reset-password", "/api/**/refresh").permitAll()
							.requestMatchers("/api/**/admin/**").hasAnyRole("ADMIN", "OWNER")
							.requestMatchers(HttpMethod.GET, "/api/v*/products/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/banners/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/categories/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/shops/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/shop-reviews/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/home").permitAll()
							.requestMatchers(HttpMethod.POST, "/api/v*/users**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/v*/search/**").permitAll()
							.anyRequest().hasAnyRole("USER", "ADMIN", "OWNER");
				})
				.exceptionHandling()
				.authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
				.and();
		// .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		String[] excludes = {
				"/api/**/sign-in",
				"/api/**/sign-up",
				"/api/**/reset-password",
				"/api/**/csrf",
				"/api/**/send-otp",
				"/api/**/refresh"
		};

		// String[] excludes = {};

		return (web) -> web.ignoring().requestMatchers(excludes);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
				Arrays.asList("https://www.shoppingcenter.com", "http://localhost:3000", "http://localhost:3080"));
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}

}
