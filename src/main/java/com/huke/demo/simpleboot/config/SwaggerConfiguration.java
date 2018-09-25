/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.huke.demo.simpleboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author huke
 * @version $Id: SwaggerConfigration.java, v 0.1 2018年09月18日 下午7:30 huke Exp $
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huke.demo.simpleboot.controller"))
                .paths(PathSelectors.ant("/users/**"))
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("Linkc")
                .termsOfServiceUrl("http://linkcode2.inc.alipay.net/")
                .description("Linkc Service")
                .version("1.0")
                .build();

    }

}