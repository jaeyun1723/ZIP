package com.ssafy.home.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy.home.controller"))
            .paths(PathSelectors.any())
            .build();
    }


    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(".ZIP(SSAFY 부동산 프로젝트)")
            .description("Whereismyhome Rest API")
            .version("0.1")
            .build();
    }
}

