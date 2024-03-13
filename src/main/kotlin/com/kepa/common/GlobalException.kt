package com.kepa.common

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.kepa.common.exception.KepaExceptionResponse
import com.kepa.common.exception.KepaException
import com.kepa.domain.log.ErrorLog
import com.kepa.domain.log.ErrorLogRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalException(
    private val errorLogRepository: ErrorLogRepository,
) {

    @ExceptionHandler(KepaException::class)
    fun kepaExceptionResponse(
        kepaException: KepaException
    ): ResponseEntity<KepaExceptionResponse> {
        return KepaExceptionResponse.toResponse(errorCode = kepaException.exceptionCode)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun notNullExceptionResponse(e: MissingKotlinParameterException): ResponseEntity<KepaExceptionResponse> {
        return KepaExceptionResponse.toNotNullableResponse("${e.path[0].fieldName} 필수로 입력해야합니다.")
    }

    @ExceptionHandler(Exception::class)
    fun saveErrorLog(e: Exception, request: HttpServletRequest) {
        errorLogRepository.save(ErrorLog(e.message,request.requestURI))
        throw e
    }
}