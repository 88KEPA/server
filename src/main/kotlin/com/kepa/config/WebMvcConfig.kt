package com.kepa.config

import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.security.LoginInterception
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val trainerRepository: TrainerRepository,
) : WebMvcConfigurer {


//    override fun addCorsMappings(registry: CorsRegistry) {
 //       registry.addMapping("/**")
   //         .allowedOrigins("http://localhost:9402")
     //       .allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
       //     .exposedHeaders("Authorization")
    //}

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginInterception(trainerRepository))
    }
}