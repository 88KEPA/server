package com.kepa.domain.user

import com.kepa.common.BaseEntity
import javax.persistence.*

@Entity
class CertNumber(
    @Column(nullable = false)
    val receiverPhoneNumber: String,
    @Column(nullable = false)
    val receiverEmail: String,
    @Column(nullable = false)
    val number: Int
): BaseEntity()