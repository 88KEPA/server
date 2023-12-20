package com.kepa.domain.account

import com.kepa.common.BaseEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Account(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val loginId: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val phone: String,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val zipCode: String,
    @Column(nullable = false)
    val jibunAddress: String,
    @Column(nullable = false)
    val jibunAddressDetail: String,
    @Column(nullable = false)
    val roadAddress: String,
    @Column(nullable = false)
    val roadAddressDetail: String,
    @Column(nullable = false)
    val birth: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val loginType: LoginType,
) : BaseEntity()