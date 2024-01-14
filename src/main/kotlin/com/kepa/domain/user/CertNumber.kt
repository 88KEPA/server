package com.kepa.domain.user

import CertType
import com.kepa.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class CertNumber(
    val receiverPhoneNumber: String? = null,
    @Column(nullable = false)
    val receiverEmail: String,
    @Column(nullable = false)
    val number: Int,
    @Enumerated(EnumType.STRING)
    val certType: CertType
): BaseEntity() {

}