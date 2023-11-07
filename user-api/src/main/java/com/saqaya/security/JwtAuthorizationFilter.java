package com.saqaya.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.saqaya.security.config.SecurityConstants;
import com.saqaya.util.SecurityUtil;

/**
 * 
 * <h1>JwtAuthorizationFilter Class</h1> 
 * <p>JWT Authorization mechanism.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	/**
	 * 
	 * Validating id request parameter exists. 
	 * Validating JWT Authorization header exists.
	 * Validating JWT Claims.
	 * Setting UsernamePasswordAuthenticationToken in Security context.
	 * Otherwise, Spring returns forbidden.
	 * 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param FilterChain
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String jwt = SecurityUtil
				.parseJwt(request)
				.orElse("");
		
	    if(id == null || id.isBlank() || jwt.isBlank()) {
			chain.doFilter(request, response);
			return;
		}
		
		try {				
			String email = SecurityUtil.parseJwtSubject(jwt);	
			SecurityUtil.validateJwtClaims(email, id);
			
			SecurityContextHolder.getContext()
			.setAuthentication(new UsernamePasswordAuthenticationToken(email, null, null));	
			
			chain.doFilter(request, response);
			
		} catch(Exception e) {
			chain.doFilter(request, response);
			return;
		}
		
	}
	
	/**
	 * 
	 * Intercepting all requests except /user POST API in Filter.
	 * 
	 * @param HttpServletRequest
	 * @return isFiltered
	 * @throws ServletException
	 * 
	 */
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals(SecurityConstants.REGISTER) && request.getMethod().equals(HttpMethod.POST.name());
    }
		
}
