package com.kepa.domain.user.trainer

enum class Gender(
    val value: String
) {
    MALE("남성"), FEMALE("여성")
}

enum class LoginType(
    val type: String
) {
    KAKAO("카카오"), ORIGIN("사이트 회원가입")
}