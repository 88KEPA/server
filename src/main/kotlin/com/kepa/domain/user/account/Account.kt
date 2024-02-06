package com.kepa.domain.user.account

import Gender
import LoginType
import Role
import com.kepa.common.BaseEntity
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Account(
    var name: String,
    var password: String,
    var phone: String,
    var email: String,
    var address: String,
    var addressMeta: String,
    var addressDetail: String,
    var birth: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val loginType: LoginType,
    @Enumerated(EnumType.STRING)
    var role: Role = Role.TRAINER,
    var withdrawAt: LocalDateTime? = null,
) : BaseEntity()