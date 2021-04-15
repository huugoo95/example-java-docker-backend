package com.slashmobility.taxly.config.constants;

import java.net.URI;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

	public static final String USER_DATA = "user.data";

	/* Locale constants */
	public static final List<String> LOCALES_STR = Arrays.asList("en", "es", "ca");
	public static final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("es"), new Locale("ca"));
	public static final Locale DEFAULT_LOCALE = new Locale("ca");

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String TOKEN_INVALID = "Token is invalid";

	/* Maximum string length of default DB */
	public static final int MAX_STRING_LENGTH = 255;

	@Value("${backend.url}")
	private String backendUrl;


	public String getBackendUrl() {
		return backendUrl;
	}

	public String getBackendHost() throws URISyntaxException {
		return new URI(getBackendUrl()).getHost();
	}


}
