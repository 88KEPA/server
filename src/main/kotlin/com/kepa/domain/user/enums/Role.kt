package com.kepa.domain.user.enums

enum class Role(
    val type: String
) {
    ADMIN("관리자"), USER("일반 사용자"), TRAINER("트레이너"), SUPER_ADMIN("슈퍼 관리자"), WITHDRAW("탈퇴 회원")
}