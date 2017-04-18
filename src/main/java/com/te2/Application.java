package com.te2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by jasonskipper on 4/17/17.
 */
@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class Application {


    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

    @Bean
    public Docket movieApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("te2-message-api")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/te2/*.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Welcome to the new TE2 Message & Subscription Service")
                .description("This is your experimenting headquarters!  <p> Please use this live documentation to play with our new API.  <p>Best Regards, Skipper.\n")
                .termsOfServiceUrl("https://www.linkedin.com/in/jasonskipper")
                .contact("Jason Skipper")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.linkedin.com/in/jasonskipper")
                .version("41.0")
                .build();
    }
}
