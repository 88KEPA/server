package com.kepa.common.exception
import org.springframework.http.HttpStatus

enum class ExceptionCode(
    var status: HttpStatus,
    val errorMessage: String,
    val identity: Int
) {
    //409
    ALREADY_INFORMATION(HttpStatus.CONFLICT, "이미 가입된 정보입니다.",40901),

    //401
    NO_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.",40101),

    //400
    NOT_MATCH_PASSWORD_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 재확인이 일치하지 않습니다.",40001),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.",40002),
    NOT_EXSISTS_INFO(HttpStatus.BAD_REQUEST, "존재하지 않는 정보입니다.",40003),
    NOT_MATCH_CERT_NUMBER(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다.", 40004),
    EXPIRE_CERT_NUMBER(HttpStatus.BAD_REQUEST, "유효시간이 지났습니다.", 40005),
    NOT_MATCH_ID_OR_PASSWORD(HttpStatus.BAD_REQUEST, "아이디 또는 비밀번호가 일치하지 않습니다.",40006),
    BAD_REQUEST_PHONE_FORMAT(HttpStatus.BAD_REQUEST, "핸드폰 번호 포맷 형태가 잘못되었습니다.", 40007),
    NOT_EXSITS_TERMS(HttpStatus.BAD_REQUEST, "약관 동의가 존재하지 않습니다.", 40008),

    //403
    NOT_ACCESS(HttpStatus.FORBIDDEN,"접근할 수 없는 권한입니다.", 40300),

    //404
    NOT_FOUND_CERT_NUMBER(HttpStatus.NOT_FOUND, "인증번호가 존재하지 않습니다.", 40401),
    TOKEN_EXPIRE(HttpStatus.NOT_FOUND,"로그인 토큰이 만료되었습니다.", 40402),
    REFRESH_TOKEN_EXPIRE(HttpStatus.NOT_FOUND,"리프레시 토큰이 만료되었습니다.", 40403),
}