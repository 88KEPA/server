package com.kepa.common.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime


class KepaExceptionResponse(
    val status: Int,
    val code: String,
    val message: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val time: LocalDateTime,
    val identity: Int,
) {

    companion object {
        fun toResponse(errorCode: ExceptionCode): ResponseEntity<KepaExceptionResponse> {
            return ResponseEntity.status(errorCode.status)
                .body(
                    KepaExceptionResponse(
                        status = errorCode.status.value(),
                        code = errorCode.status.name,
                        message = errorCode.errorMessage,
                        time = LocalDateTime.now(),
                        identity = errorCode.identity
                    )
                )
        }

        fun toNotNullableResponse(message: String): ResponseEntity<KepaExceptionResponse> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body( KepaExceptionResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    code = HttpStatus.BAD_REQUEST.name,
                    message = message,
                    time = LocalDateTime.now(),
                    identity = 40000
                ))
        }
    }
}