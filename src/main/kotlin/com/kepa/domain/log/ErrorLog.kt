package com.kepa.domain.log

import com.kepa.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class ErrorLog(
    @Lob
    @Column(columnDefinition = "TEXT")
    val message: String?,
    val requestURI: String,
) : BaseEntity() {
}