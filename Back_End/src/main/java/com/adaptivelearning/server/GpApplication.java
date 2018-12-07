package com.adaptivelearning.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

@SpringBootApplication

@EntityScan(basePackageClasses = {
        GpApplication.class,
        Jsr310JpaConverters.class
})


public class GpApplication {


    public static void main(String[] args) {
        SpringApplication.run(GpApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
