package com.kepa.application.user.domain

import Role
import com.kepa.common.BaseEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class TrainerRefreshToken(
    val email: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    val token: String,
    val expireAt: Long
) : BaseEntity() {
}