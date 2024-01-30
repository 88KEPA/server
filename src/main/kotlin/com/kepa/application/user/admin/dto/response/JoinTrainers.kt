package com.kepa.application.user.admin.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalDateTime

data class JoinTrainers(
    val name: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birth: LocalDate,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
)