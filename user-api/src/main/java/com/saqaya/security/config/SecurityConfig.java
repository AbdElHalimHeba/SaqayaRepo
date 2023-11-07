package com.saqaya.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.saqaya.security.JwtAuthorizationFilter;

/**
 * 
 * <h1>SecurityConfig Class</h1> 
 * <p>This class sets Spring Security configuration.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthorizationFilter filter;
			
	/**
	 * 
	 * SecurityFilterChain bean disables CSRF parameter in favor of JWT.
	 * URL /user POST permits anonymous requests.
	 * All URLs must be authenticated by setting UsernamePasswordAuthenticationToken on Security context.
	 * All URLs are intercepted by JwtAuthorizationFilter for JWT authorization. 
	 * Spring Session is STATELESS which means that there's no session in the application managed by Spring.
	 * 
	 * @param HttpSecurity
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http
		.csrf((csrf) -> csrf.disable())
		.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.anyRequest().authenticated()
		)
		.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement((sessionManagement) ->
			sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		).build();				
	}
		
}