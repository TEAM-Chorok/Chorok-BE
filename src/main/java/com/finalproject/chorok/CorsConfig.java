package com.finalproject.chorok;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://chorok-test.s3-website.ap-northeast-2.amazonaws.com/")
                .allowedMethods("GET","POST","PUT","DELETE","PATCH")
                .allowCredentials(true)
                .maxAge(3000);
    }
}
