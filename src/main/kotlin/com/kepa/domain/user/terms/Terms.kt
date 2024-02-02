package com.kepa.domain.user.terms

import com.kepa.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class Terms(
    @Lob
    @Column(columnDefinition = "TEXT")
    val content: String,
    val title: String,
) : BaseEntity(){
}