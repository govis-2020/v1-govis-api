package com.koreahacks.govis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@EnableJpaRepositories
@SpringBootApplication
public class GovisApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovisApplication.class, args);
    }

}
