package com.kepa.application.trainer.dto.response

data class LoginToken(
    val grantType: String = "Bearer",
    val accessToken: String,
    val refreshToken: String,
)

data class MessageSendStatus(
    val result_code: Int,
    val message: String,
    val msg_id: Int,
    val success_cnt: Int,
    val error_cnt: Int,
    val msg_type: String,
)