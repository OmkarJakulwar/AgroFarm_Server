package com.farmersmarket.freshproducemarketplace;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AfConfig implements WebMvcConfigurer {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	  public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	    .allowedOrigins("http://localhost:4200") // Allow requests from frontend domain
        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specified HTTP methods
        .allowedHeaders("*") // Allow all headers
        .allowCredentials(true); // Allow sending cookies
	  }

}
