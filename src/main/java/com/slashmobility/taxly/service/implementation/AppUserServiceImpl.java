package com.slashmobility.taxly.service.implementation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.slashmobility.taxly.config.constants.SecurityConstants;
import com.slashmobility.taxly.database.entity.Admin;
import com.slashmobility.taxly.database.entity.AppUser;
import com.slashmobility.taxly.database.entity.User;
import com.slashmobility.taxly.database.repository.AdminRepository;
import com.slashmobility.taxly.database.repository.UserRepository;
import com.slashmobility.taxly.service.interfaces.AppUserService;

@Service(value = "apiUserService")
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	private SecurityConstants securityConstants;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean accountActive = true;
		boolean isAdmin = false;

		User appUser = new User();
		Admin appAdmin = new Admin();

		if (securityConstants.isUser(username)) {
			Optional<User> user = userRepository.findById(securityConstants.getUserId(username));

			if (!user.isPresent()) {
				throw new UsernameNotFoundException("Not found: " + username);
			}

			appUser = user.get();
			
		} else if (securityConstants.isAdmin(username)) {
			Optional<Admin> adminOpt = adminRepository.findById(securityConstants.getAdminId(username));
			if (!adminOpt.isPresent()) {
				throw new UsernameNotFoundException("Not found");
			}
			appAdmin = adminOpt.get();
			isAdmin = true;

		} else {
			throw new UsernameNotFoundException("Not found: " + username);
		}
		
		return new org.springframework.security.core.userdetails.User(
        		username, 
        		(isAdmin) ? appAdmin.getPassword() : "", 
        		accountActive,
        		accountNonExpired,
        		credentialsNonExpired,
        		accountNonLocked,
        		(isAdmin) ? getAdminAuthority(appAdmin) : getUserAuthority(appUser));

	}

	@Override
	public Set<SimpleGrantedAuthority> getAdminAuthority(AppUser appUser) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_ + "ADMIN"));
		
		return authorities;
	}
	
	@Override
	public Set<SimpleGrantedAuthority> getUserAuthority(AppUser appUser) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(SecurityConstants.ROLE_ + "USER"));
		
		return authorities;
	}

}