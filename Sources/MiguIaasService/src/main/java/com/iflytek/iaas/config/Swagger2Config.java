package com.iflytek.iaas.config;

import com.iflytek.iaas.consts.EnvConsts;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 〈swagger配置〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("${spring.profiles.active}")
    private String environment;

    @Bean
    public Docket createRestApi() {
        ApiSelectorBuilder apiBuiler = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select();
        //生产环境不生成接口文档
        if (EnvConsts.PROD.equals(environment)) {
            return apiBuiler.build();
        }

        return apiBuiler.apis(RequestHandlerSelectors.basePackage("com.iflytek.iaas.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MiGu基础平台接口文档")
                .description("Backend 接口描述")
                .contact(new Contact("ruizhao3", "http://www.iflytek.com/", "ruizhao3@iflytek.com"))
                .version("1.0")
                .build();
    }
}
