package com.barrage.webcontroller.config;

import com.barrage.webcontroller.WebcontrollerApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(initApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(WebcontrollerApplication.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo initApiInfo() {
        return new ApiInfoBuilder()
                .title("Barrage Web Api Document")
                .description("no any information")
                .version("1.0.0")
                .contact(new Contact("TomShiDi", "https://blog.csdn.net/Tomsidi", "1341109792@qq.com"))
                .build();
    }
}
