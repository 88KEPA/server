package com.kepa.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val status: HttpStatus,
    val errorMessage: String,
    val identity: Int
) {
    ALREADY_INFORMATION(HttpStatus.CONFLICT, "이미 가입된 정보입니다.",40901),
    NO_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "권한이 올바르지 않습니다.",40101),
    NOT_MATCH_PASSWORD_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 재확인이 일치하지 않습니다.",40001),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.",40002),
    NOT_EXSISTS_INFO(HttpStatus.BAD_REQUEST, "존재하지 않는 정보입니다.",40003),

}