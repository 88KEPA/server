package com.kepa.domain.partner.enums

enum class ApproveStatus(
    val content: String,
) {
    APPROVE("승인"), READY("대기"), REFUSE("거절")
}