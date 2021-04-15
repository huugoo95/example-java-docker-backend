package com.slashmobility.taxly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slashmobility.taxly.config.SwaggerConfig;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping(path = {SwaggerConfig.API})
public class HealthController {

	@GetMapping("/health")
	public String getHealth() {
		return "I'm alive";
	}

}
