package com.kepa.common.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime


class KeapExceptionResponse(
    val status: Int,
    val code: String,
    val message: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val time: LocalDateTime,
    val identity: Int,
) {

    companion object {
        fun toResponse(errorCode: ExceptionCode): ResponseEntity<KeapExceptionResponse> {
            return ResponseEntity.status(errorCode.status)
                .body(
                    KeapExceptionResponse(
                        status = errorCode.status.value(),
                        code = errorCode.status.name,
                        message = errorCode.errorMessage,
                        time = LocalDateTime.now(),
                        identity = errorCode.identity
                    )
                )
        }

        //fun toNotNullableResponse()

    }
}