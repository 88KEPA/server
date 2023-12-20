package com.kepa.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val status: HttpStatus,
    val errorMessage: String,
) {
    ALREADY_INFORMATION(HttpStatus.CONFLICT, "이미 가입된 정보입니다."),
    NOT_MATCH_PASSWORD_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 재확인이 일치하지 않습니다.")
}