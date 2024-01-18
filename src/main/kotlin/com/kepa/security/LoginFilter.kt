package com.kepa.security

import com.kepa.token.TokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class LoginFilter(
    private val tokenProvider: TokenProvider,
    ) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain) {
        tokenProvider.resolveToken(request = request as? HttpServletRequest?)?.also {
            val authentication = tokenProvider.getAuthentication(it)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request,response)
    }
}