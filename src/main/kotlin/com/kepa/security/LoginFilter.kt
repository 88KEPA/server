package com.kepa.security

import com.kepa.token.TokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class LoginFilter(
    private val tokenProvider: TokenProvider,
    ) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("동작")
        tokenProvider.resolveToken(request)?.let {
            val validateToken = tokenProvider.validateToken(it)
            if(validateToken) {
                val token = it.split(" ")[1].trim()
                val auth = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        }
        filterChain.doFilter(request,response)
    }
}