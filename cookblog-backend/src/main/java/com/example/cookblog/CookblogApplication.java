package com.example.cookblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class CookblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookblogApplication.class, args);
    }

}
