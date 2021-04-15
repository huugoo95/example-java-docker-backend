package com.slashmobility.taxly.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

	@Bean
	public AuditorAware<String> auditorProvider() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) 
			return () -> Optional.ofNullable("taxly");
		
		return () -> Optional.ofNullable(
				SecurityContextHolder.getContext().getAuthentication().getName());
	}
}
