package com.kepa.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaExceptionResponse
import org.springframework.http.MediaType
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * packageName    : com.kepa.security
 * fileName       : TokenAuthenticationEntryPoint
 * author         : hoewoonjeong
 * date           : 3/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/13/24        hoewoonjeong               최초 생성
 */
@Component
class TokenAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: org.springframework.security.core.AuthenticationException?
    ) {
        val errorResponse = KepaExceptionResponse.toResponse(ExceptionCode.NO_AUTHENTICATION)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 401
        response.writer.write(objectMapper.writeValueAsString(errorResponse.body))
    }
}