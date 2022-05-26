package com.finalproject.chorok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@ServletComponentScan("lecturer")
@EnableJpaAuditing
@SpringBootApplication
public class ChorokApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:application.yml,"
            + "classpath:main.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ChorokApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
        System.out.println("current time : " + LocalDateTime.now());

    }

}