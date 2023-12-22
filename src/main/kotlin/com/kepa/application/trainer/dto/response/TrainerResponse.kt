package com.kepa.application.trainer.dto.response

data class LoginToken(
    val grantType: String = "Bearer",
    val accessToken: String,
    val refreshToken: String,
)

