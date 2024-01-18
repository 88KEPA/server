package com.kepa.domain.user.trainer

import Gender
import LoginType
import Role
import com.kepa.common.BaseEntity
import com.kepa.domain.user.User
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Trainer(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val phone: String,
    @Column(nullable = false)
    override val email: String,
    @Column(nullable = false)
    val address: String,
    val addressMeta: String,
    val addressDetail: String,
    @Column(nullable = false)
    val birth: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val loginType: LoginType,
    @Enumerated(EnumType.STRING)
    override val role: Role = Role.TRAINER
) : BaseEntity(), User