package com.kepa.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaExceptionResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler(
    val objectMapper: ObjectMapper,
) : AccessDeniedHandler{
    override fun handle(request: HttpServletRequest?, response: HttpServletResponse, accessDeniedException: AccessDeniedException?) {
        val errorResponse = KepaExceptionResponse.toResponse(ExceptionCode.NOT_ACCESS)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(errorResponse.body))
        response.status = 403
    }
}