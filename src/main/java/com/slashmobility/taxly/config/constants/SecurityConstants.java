package com.slashmobility.taxly.config.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
	
	private static final String ADMIN = "ADMIN_";
	private static final String USER = "USER_";
	public static final String ROLE_ = "ROLE_";

    @Value("${access.token.validity.seconds}")
    private long accessTokenValiditySeconds;
    
    @Value("${signing.key}")
    private String signingKey;
    
    @Value("${token.prefix}")
    private String tokenPrefix; 
    
    @Value("${header.string}")
    private String headerString;
    
    @Value("${authorities.key}")
    private String authoritiesKey;

	public long getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public String getHeaderString() {
		return headerString;
	}

	public String getAuthoritiesKey() {
		return authoritiesKey;
	}
	
	public String generateAdminUsername(long id) {
		return ADMIN + id;
	}
	
	public boolean isAdmin(String username) {
		return username.contains(ADMIN);
	}
	
	public Long getAdminId(String username) {
		return Long.valueOf(username.replace(ADMIN, ""));
	}
	
	public String generateUserUsername(long id) {
		return USER + id;
	}
	
	public boolean isUser(String username) {
		return username.contains(USER);
	}
	
	public Long getUserId(String username) {
		return Long.valueOf(username.replace(USER, ""));
	}
}
