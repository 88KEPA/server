package com.kepa.domain.user.terms

import com.kepa.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Terms(
    @Column(name = "TEXT")
    val content: String,
    val title: String,
) : BaseEntity(){
}