package com.kepa.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.lang.RuntimeException
import java.time.LocalDateTime

class KepaException(
    val exceptionCode: ExceptionCode,
) : RuntimeException() {
    val time: LocalDateTime = LocalDateTime.now()
    val status: HttpStatus = exceptionCode.status
    val errorMessage: String = exceptionCode.errorMessage
    fun toResponse(): ResponseEntity<KepaException> {
        return ResponseEntity.status(exceptionCode.status)
            .body(this)
    }
}