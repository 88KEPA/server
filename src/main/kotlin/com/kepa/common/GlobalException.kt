package com.kepa.common

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalException {

    @ExceptionHandler(KepaException::class)
    fun kepaExceptionResponse(
        kepaException: KepaException
    ): ResponseEntity<KepaException> {
        return KepaException(kepaException.exceptionCode).toResponse()
    }
}