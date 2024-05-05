package com.kepa.application.user.dto.request

import com.kepa.domain.user.enums.Role


data class LoginUserInfo(val id: Long, val role: Role)
