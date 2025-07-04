package com.kepa.domain.user

import com.kepa.common.BaseEntity
import com.kepa.domain.user.enums.CertType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class CertNumber(
    val receiverPhoneNumber: String? = null,
    val receiverEmail: String? =null,
    @Column(nullable = false)
    val number: Int,
    @Enumerated(EnumType.STRING)
    val certType: CertType
): BaseEntity() {

}