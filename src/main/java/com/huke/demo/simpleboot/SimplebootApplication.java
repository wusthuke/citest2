package com.huke.demo.simpleboot;

import com.huke.demo.simpleboot.config.FastjsonConfiguration;
import com.huke.demo.simpleboot.config.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(
        basePackages = {"com.huke.demo.simpleboot"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.huke.demo.simpleboot.controller.CustomErrorController")
        })
@Import({SwaggerConfiguration.class, FastjsonConfiguration.class})
public class SimplebootApplication {

    @Autowired
    private ApplicationContext context;

    // @PostConstruct
    public void startup() {
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(SimplebootApplication.class, args);
    }
}
