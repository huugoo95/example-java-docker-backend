package com.slashmobility.taxly.config;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.slashmobility.taxly.config.constants.Constants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String API = "/api";
	public static final String V1 = "/v1";
	public static final String V2 = "/v2";
	public static final String API_TITLE = "Taxly Backend API";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	public static final String TAG_AUTH = "Authentication";
	
	@Autowired
	private Constants constants;
	
	private Docket api(String version) throws URISyntaxException {
		return new Docket(DocumentationType.SWAGGER_2)
        		.host(constants.getBackendUrl())
        		.groupName(version)
                .select()
	            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	            .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo(version))
                .securitySchemes(securitySchemes())
                .securityContexts(new ArrayList<>(securityContext()));
	}
	
	
	private ApiInfo getApiInfo(String version) {
		return new ApiInfoBuilder().version(version).title(API_TITLE).build();
	}

	@Bean
    public Docket apiV1() throws URISyntaxException {
        return api(V1);
    }
	
	@Bean
    public Docket apiV2() throws URISyntaxException {
        return api(V2);
    }

    private static List<SecurityScheme> securitySchemes() {

        ArrayList<SecurityScheme> array = new ArrayList<>();
        array.add(new ApiKey("Bearer", "Authorization", "header"));

        return array;
    }

    private List<SecurityContext> securityContext() {
    	ArrayList<SecurityContext> array = new ArrayList<>();
    	SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    	array.add(securityContext);
        return array;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        ArrayList<SecurityReference> array = new ArrayList<>();
        array.add(
                new SecurityReference("Bearer", authorizationScopes));
        return array;
    }

}
