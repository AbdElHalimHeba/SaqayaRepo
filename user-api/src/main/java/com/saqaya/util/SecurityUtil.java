package com.saqaya.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

import com.saqaya.security.config.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * <h1>SecurityUtil Class</h1> 
 * <p>This util is concerned with JWT & Spring Security static helpers.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
public class SecurityUtil {

	/**
	 * 
	 * Generates JWT by:
	 * Set Claims from subject.
	 * Set Expiration to 1 hr.
	 * Sign with JWT secret.
	 * 
	 * @param subject
	 * @return JWT
	 * 
	 */
	public static String generateJWT(String subject) {
		
		Claims claims = Jwts.claims().setSubject(subject);
				
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET.getBytes())
                .compact();
	}
	
	/**
	 * 
	 * Allowing exposure of Authorization header in HttpServletResponse.
	 * Setting Authorization header in HttpServletResponse with JWT.
	 * 
	 * @param JWT
	 * @param HttpServletResponse
	 * 
	 */
	public static void addJwtToResponse(String jwt, HttpServletResponse response) {
		
		response.addHeader(SecurityConstants.AUTH, SecurityConstants.BEARER.concat(jwt));
		response.addHeader(SecurityConstants.ACEH, SecurityConstants.AUTH);
    }
	
	/**
	 * 
	 * Parsing JWT from Authorization header in HttpServletRequest.
	 * Returns either JWT if exists or empty Optional otherwise.
	 * 
	 * @param HttpServletRequest
	 * @return JWT
	 * 
	 */
	public static Optional<String> parseJwt(HttpServletRequest request) {

        String jwt = request.getHeader(SecurityConstants.AUTH);
        
        if(jwt == null || !jwt.startsWith(SecurityConstants.BEARER)) return Optional.empty();
        return Optional.of(jwt.substring(SecurityConstants.BEARER.length()));        
    }
	
	/**
	 * 
	 * Returns JWT Claims Subject.
	 * 
	 * @param JWT
	 * @return subject
	 * 
	 */
	public static String parseJwtSubject(String jwt) {
		return Jwts.parser()
		.setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
		.parseClaimsJws(jwt)
		.getBody()
		.getSubject();
	}
	
	/**
	 * 
	 * Validates that parsed JWT Claims Subject equals to generated id.
	 * Throws AccessDeniedException otherwise.
	 * @see generateSHA1Hash
	 * 
	 * @param Subject
	 * @param id
	 * 
	 */
	public static void validateJwtClaims(String subject, String id) {
		if(!generateSHA1Hash(subject).equals(id))
			throw new AccessDeniedException("Generated id does not match email");
	}
	
	/**
	 * 
	 * Salting input with SHA1_SALT.
	 * Hashing it with SHA-1 Algorithm.
	 * Then hex-encode Hash.
	 * 
	 * @param input
	 * @return hash
	 * 
	 */
	public static String generateSHA1Hash(String input) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-1");
            String salt = SecurityConstants.SHA1_SALT;
            byte[] saltedPassword = (input.concat(salt)).getBytes();
            byte[] hash = digester.digest(saltedPassword);

            return String.format("%040x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
        	return input;
        }
    }
}
