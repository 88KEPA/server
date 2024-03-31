package com.kepa.security

import com.kepa.domain.user.account.RefreshTokenRepository
import com.kepa.token.TokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginFilter(
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenAuthenticationEntryPoint: TokenAuthenticationEntryPoint,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        tokenProvider.resolveToken(request = request as? HttpServletRequest?)?.also {
            val loginToken = it.split(" ")[1]
            val findToken = refreshTokenRepository.findByAccessToken(loginToken)

            if (!it.toLowerCase()
                    .startsWith("bearer ") || !tokenProvider.validateToken(loginToken)
            ) {
                tokenAuthenticationEntryPoint.commence(
                    request = request,
                    response = response,
                    authException = null
                )
                return
            }
            val authentication = tokenProvider.getAuthentication(loginToken)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}
