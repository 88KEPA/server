package com.kepa.domain.user.account

import Role
import com.kepa.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class RefreshToken(
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val expireAt: LocalDateTime,
    @OneToOne(fetch = FetchType.LAZY)
    val account: Account,
    @Column(nullable = false)
    val accessToken: String,
    @Column(nullable = false)
    val accessTokenExpireAt: LocalDateTime
) : BaseEntity()