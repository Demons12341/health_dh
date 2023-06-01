package com.dh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.dh.**"})
public class HealthMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthMobileApplication.class, args);
    }

}
