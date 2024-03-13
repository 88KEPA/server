package com.kepa.config

import com.kepa.application.user.dto.request.LoginUserInfo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {
    companion object {
        val HEADER_NAME  = "Authorization"
    }

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.OAS_30)
            .ignoredParameterTypes(LoginUserInfo::class.java)
            .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.ant("/api/**"))
                    .build()
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder().description("트레이너 입회를 위한 API 명세서")
            .title("트레이너 입회 API 명세서").build()
    }
    private fun securityContext() : SecurityContext = SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build()

    private fun apiKey(): ApiKey = ApiKey(HEADER_NAME, HEADER_NAME, "header")

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEveryThing")
        val authorizationScopes: Array<AuthorizationScope> = arrayOf(authorizationScope)
        return listOf(SecurityReference(HEADER_NAME, authorizationScopes))

    }
}