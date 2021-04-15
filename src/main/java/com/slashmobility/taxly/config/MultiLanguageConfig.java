package com.slashmobility.taxly.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import com.slashmobility.taxly.config.constants.Constants;


@Configuration
public class MultiLanguageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	// Do not rename that bean, otherwise Spring MVC won't pick it up!
	@Bean("localeResolver") 
	public LocaleResolver acceptHeaderLocaleResolver() {
	    AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();

	    resolver.setDefaultLocale(Constants.DEFAULT_LOCALE);
	    resolver.setSupportedLocales(Constants.LOCALES);

	    return resolver;
	}
	
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader("Accept-Language");
		return headerLang == null || headerLang.isEmpty() ? Constants.DEFAULT_LOCALE
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), Constants.LOCALES);
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
		rs.setBasenames("lang/messages", "lang/messages", "lang/messages");
		rs.setDefaultEncoding("UTF-8");
		rs.setUseCodeAsDefaultMessage(true);
		return rs;
	}
}
