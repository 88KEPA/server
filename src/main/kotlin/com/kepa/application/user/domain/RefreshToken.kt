package com.kepa.application.user.domain

import Role
import com.kepa.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class RefreshToken(
    @Column(nullable = false)
    val email: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val expireAt: Long
) : BaseEntity() {
}