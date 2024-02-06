package com.kepa.domain.user.account

import Role
import com.kepa.common.BaseEntity
import javax.persistence.*

@Entity
class RefreshToken(
    @Enumerated(EnumType.STRING)
    val role: Role,
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val expireAt: Long,
    @OneToOne(fetch = FetchType.LAZY)
    val account: Account,
) : BaseEntity() {
}