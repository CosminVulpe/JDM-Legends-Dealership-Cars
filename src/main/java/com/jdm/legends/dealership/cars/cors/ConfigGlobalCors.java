package com.jdm.legends.dealership.cars.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigGlobalCors  implements WebMvcConfigurer {
    private final int maxAge;

    public ConfigGlobalCors(@Value("${cors.max-age-time}") int maxAge) {
        this.maxAge = maxAge;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000/")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(maxAge);
    }
}
