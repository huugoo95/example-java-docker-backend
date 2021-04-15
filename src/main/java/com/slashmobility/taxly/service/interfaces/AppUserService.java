package com.slashmobility.taxly.service.interfaces;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.slashmobility.taxly.database.entity.AppUser;

public interface AppUserService {

	Set<SimpleGrantedAuthority> getAdminAuthority(AppUser appUser);
	
	Set<SimpleGrantedAuthority> getUserAuthority(AppUser appUser);
	
}
