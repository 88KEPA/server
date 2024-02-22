package com.kepa.config

import com.kepa.domain.user.account.RefreshTokenRepository
import com.kepa.security.JwtAccessDeniedHandler
import com.kepa.security.LoginFilter
import com.kepa.token.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val refreshTokenRepository: RefreshTokenRepository,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        var config = CorsConfiguration()
        config.allowedOrigins = listOf(
            "http://localhost:9402/", "http://localhost:9401/", "https://www.kepa.associates/",
            "https://admin.kepa.associates/"
        )
        config.allowedMethods = listOf("OPTIONS", "GET", "POST", "PUT", "DELETE")
        config.allowedHeaders = listOf("*")
        config.allowCredentials = true

        var source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                LoginFilter(tokenProvider, refreshTokenRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/api/trainer/info/**")
            .hasAnyAuthority(Role.TRAINER.name, Role.ADMIN.name, Role.SUPER_ADMIN.name)
            .antMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.name, Role.SUPER_ADMIN.name)
            .antMatchers("/api/withdraw").hasAnyAuthority(
                Role.ADMIN.name,
                Role.SUPER_ADMIN.name,
                Role.TRAINER.name,
                Role.USER.name
            )
            .antMatchers("/api/token").hasAnyAuthority(
                Role.ADMIN.name,
                Role.SUPER_ADMIN.name,
                Role.TRAINER.name,
                Role.USER.name
            )
            .antMatchers("/api/logout").hasAnyAuthority(
                Role.ADMIN.name,
                Role.SUPER_ADMIN.name,
                Role.TRAINER.name,
                Role.USER.name
            )
            .antMatchers("/**")
            .permitAll()
            .anyRequest().authenticated()

    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v2/api-docs",
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
            "/h2/**"
        );
    }
}