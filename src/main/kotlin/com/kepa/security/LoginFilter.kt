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
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        tokenProvider.resolveToken(request = request as? HttpServletRequest?)?.also {
            val loginToken = it.split(" ")[1]
            if(!tokenProvider.validateToken(loginToken)) {
                println("asdasdsa")
                response.sendError(401,"aa")
                return
            }
            val authentication = tokenProvider.getAuthentication(loginToken)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

}
