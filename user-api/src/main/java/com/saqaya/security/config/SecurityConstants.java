package com.saqaya.security.config;

/**
 * 
 * <h1>SecurityConstants Class</h1> 
 * <p>This class contains the Spring Security constants.</p>
 *
 * @author Heba Abd El-Halim
 *
 */
public final class SecurityConstants {

    public static final String JWT_SECRET = "450d0b0db2bcf4adde5032eca1a7c416e560cf44";
    public static final long JWT_EXPIRATION = 3600000; 
    
    public static final String ACEH = "access-control-expose-headers";
    public static final String AUTH = "Authorization";
    public static final String BEARER = "Bearer ";
    
    public static final String SHA1_SALT = "450d0b0db2bcf4adde5032eca1a7c416e560cf44";
    
  	public static final String REGISTER = "/user";

}
