package com.kepa.domain.user.account

import com.kepa.common.BaseEntity
import com.kepa.domain.user.enums.Gender
import com.kepa.domain.user.enums.LoginType
import com.kepa.domain.user.enums.Role
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Account(
    var name: String,
    var password: String,
    var phone: String,
    var email: String,
    var address: String,
    var addressMeta: String,
    var addressDetail: String,
    var birth: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val loginType: LoginType,
    @Enumerated(EnumType.STRING)
    var role: Role = Role.TRAINER,
    var withdrawAt: LocalDateTime? = null,
    @ColumnDefault("false")
    var isResource: Boolean = false
) : BaseEntity() {

    fun update(email: String, address: String, addressMeta: String, addressDetail: String, phone: String) {
        this.email = email
        this.address = address
        this.addressMeta = addressMeta
        this.addressDetail = addressDetail
        this.phone = phone
    }
    fun updateIsResource(isResource: Boolean) {
        this.isResource = isResource;
    }
}