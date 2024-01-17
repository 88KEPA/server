package com.kepa.application.user.trainer.dto.response

import java.time.LocalDate

data class LoginToken(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt : Long,
    val refreshTokenExpiredAt : Long,
)

data class MessageSendStatus(
    val result_code: Int,
    val message: String,
    val msg_id: Int,
    val success_cnt: Int,
    val error_cnt: Int,
    val msg_type: String,
)