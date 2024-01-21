package com.kepa.application.user.trainer.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class LoginToken(
    val id: Long,
    val grantType: String = "Bearer",
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val accessTokenExpiredAt : Date,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val refreshTokenExpiredAt : Date,
)

data class MessageSendStatus(
    val result_code: Int,
    val message: String,
    val msg_id: Int,
    val success_cnt: Int,
    val error_cnt: Int,
    val msg_type: String,
)