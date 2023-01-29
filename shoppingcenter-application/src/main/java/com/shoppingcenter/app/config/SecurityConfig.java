package com.shoppingcenter.app.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shoppingcenter.app.security.CustomPermissionEvaluator;
import com.shoppingcenter.app.security.Http401UnauthorizedEntryPoint;
import com.shoppingcenter.data.user.UserRepo;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	private static final String COGNITO_GROUPS = "cognito:groups";

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	// }

	@Autowired
	private UserRepo userRepo;

	@Bean
	static MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
		DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(new CustomPermissionEvaluator());
		return handler;
	}

	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			String role = userRepo.getUserById(jwt.getSubject()).map(v -> v.getRole()).orElse("USER");
			// System.out.println("Sub: " + jwt.getSubject());
			return jwt.getClaimAsStringList(COGNITO_GROUPS).stream().map(group -> {
				// String role = StringUtils.hasText(group) ? group : "USER";
				return new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
			}).collect(Collectors.toList());
		});

		return converter;
	}

	@Bean
	SecurityFilterChain clientApiFilterChain(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.securityMatcher("/api/**")
				.authorizeHttpRequests(authz -> {
					authz
							// .antMatchers("/api/**/sign-in", "/api/**/sign-up", "/api/**/social-sign-in",
							// "/api/**/reset-password", "/api/**/refresh").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/products/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/banners/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/categories/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/shops/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/shop-reviews/**").permitAll()
							.requestMatchers(HttpMethod.GET, "/api/**/home").permitAll()
							.anyRequest()
							.authenticated();
				})
				.exceptionHandling()
				.authenticationEntryPoint(new Http401UnauthorizedEntryPoint())
				.and()
				.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

		return http.build();
	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// String[] excludes = { "/api/**/sign-in", "/api/**/sign-up",
		// "/api/**/forgot-password" };

		String[] excludes = {};

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
