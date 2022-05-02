package com.finalproject.chorok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ServletComponentScan("lecturer")
@EnableJpaAuditing
@SpringBootApplication
public class ChorokApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(ChorokApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
//@ServletComponentScan("lecturer")
//@SpringBootApplication
//public class ChorokApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(ChorokApplication.class, args);
//    }
//}
