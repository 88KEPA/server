package com.kepa.domain.partner

import com.kepa.common.BaseEntity
import com.kepa.domain.partner.enums.ApproveStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Partner(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val phone: String,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val content: String,
    @Column(nullable = false)
    val organization: String,
    @Enumerated(EnumType.STRING)
    val approveStatus: ApproveStatus = ApproveStatus.READY,
    val position: String,
) : BaseEntity(){
}