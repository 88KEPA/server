package com.kepa.domain.user.enums

enum class CertType(
    val type: String,
) {
    EMAIL("이메일"), PHONE("핸드폰"), FIND("찾기"), FIND_RESULT("찾기 결과")
}