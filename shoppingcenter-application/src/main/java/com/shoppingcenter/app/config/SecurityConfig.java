package com.shoppingcenter.app.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shoppingcenter.app.security.Http401UnauthorizedEntryPoint;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
	
	private static final String COGNITO_GROUPS = "cognito:groups";
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		
		converter.setJwtGrantedAuthoritiesConverter(jwt -> {
			return jwt.getClaimAsStringList(COGNITO_GROUPS).stream().map(group -> new SimpleGrantedAuthority("ROLE_" + group)).collect(Collectors.toList());
		});
		
		
		return converter;
	}

	@Bean
	public SecurityFilterChain clientApiFilterChain(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.antMatcher("/api/**")
			.authorizeHttpRequests(authz -> {
				authz
					//.antMatchers("/api/**/sign-in", "/api/**/sign-up", "/api/**/social-sign-in", "/api/**/reset-password", "/api/**/refresh").permitAll()
					.antMatchers(HttpMethod.GET, "/api/**/product/**").permitAll()
					.antMatchers(HttpMethod.GET, "/api/**/brand/**").permitAll()
					.antMatchers(HttpMethod.GET, "/api/**/category/**").permitAll()
					.antMatchers(HttpMethod.GET, "/api/**/shop/**").permitAll()
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
	public WebSecurityCustomizer webSecurityCustomizer() {
		//String[] excludes = { "/api/**/sign-in", "/api/**/sign-up", "/api/**/forgot-password" };
		
		String[] excludes = {};

		return (web) -> web.ignoring().antMatchers(excludes);
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://www.shoppingcenter.com", "http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}