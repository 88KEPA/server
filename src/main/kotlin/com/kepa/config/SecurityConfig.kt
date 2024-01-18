package com.kepa.config

import com.kepa.token.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
       http
           .cors().and()
           .csrf().disable()
           .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
           //.addFilterBefore(LoginFilter(tokenProvider),UsernamePasswordAuthenticationFilter::class.java)
           .authorizeRequests()
           //.antMatchers("/api/trainer/**").hasAnyAuthority(Role.TRAINER.name)
           .antMatchers("/**")
           .permitAll()
           .anyRequest().authenticated()

    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers( "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/file/**",
            "/image/**",
            "/swagger/**",
            "/swagger-ui/**",
            "/h2/**");
    }
}