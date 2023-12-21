package com.kepa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.ant("/api/**"))
                    .build()
                    .useDefaultResponseMessages(false)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder().description("트레이너 입회를 위한 API 명세서")
            .title("트레이너 입회 API 명세서").build()
    }
}