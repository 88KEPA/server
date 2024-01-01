package com.kepa.domain.user

import com.kepa.common.BaseEntity
import com.kepa.domain.user.trainer.Trainer
import javax.persistence.*

@Entity
class CertNumber(
    val number: Int,
    val receiverEmail: String,
): BaseEntity()