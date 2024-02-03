package com.kepa.common

import com.kepa.common.exception.KeapExceptionResponse
import com.kepa.common.exception.KepaException
import com.kepa.domain.log.ErrorLogRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class GlobalException(
    private val errorLogRepository: ErrorLogRepository,
) {

    @ExceptionHandler(KepaException::class)
    fun kepaExceptionResponse(
        kepaException: KepaException
    ): ResponseEntity<KeapExceptionResponse> {
        return KeapExceptionResponse.toResponse(errorCode = kepaException.exceptionCode)
    }

}