package com.nitconfbackend.nitconf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class NitconfApplication {

	public static void main(String[] args) {
		SpringApplication.run(NitconfApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods(RequestMethod.GET.toString(),
						RequestMethod.POST.toString(), RequestMethod.PUT.toString(), RequestMethod.DELETE.toString());
			};
		};
	}
}
