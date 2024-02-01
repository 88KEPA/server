package com.kepa.config

import com.kepa.domain.user.account.AccountRepository
import com.kepa.security.LoginInterception
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val accountRepository: AccountRepository,
) : WebMvcConfigurer {


    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:9402","http://localhost:9401" ,"https://www.kepa.associates/")
            .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
            .allowedHeaders("Authorization", "Origin","X-Requested-With" ,"Content-Type", "Accept")
            .allowCredentials(true)
            .maxAge(3600)

    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginInterception(accountRepository))
    }
}