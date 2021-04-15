package com.slashmobility.taxly.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.slashmobility.taxly.config.constants.SecurityConstants;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Resource(name = "apiUserService")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @Autowired
    private SecurityConstants securityConstants;
    
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    	logger.debug(req.getRequestURI());
        String header = req.getHeader(securityConstants.getHeaderString());
        String username = null;
        String authToken = null;
        
        
        if (header != null && header.startsWith(securityConstants.getTokenPrefix())) {
            authToken = header.replace(securityConstants.getTokenPrefix(),"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.info("an error occured during getting username from token", e);
            } catch(Exception e){
                logger.info("There was a problem with token authentication");
            }
        } else {
            // DELETED logger.warn("couldn't find bearer string, will ignore the header");
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails) && userDetails.isAccountNonLocked()) {
            	
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                    
            }
        }

        try {
			chain.doFilter(req, res);
		} catch (Exception e) {
			logger.info("Authentication Failed. Caused: " + e.getCause());
		} 
    }
    

}
