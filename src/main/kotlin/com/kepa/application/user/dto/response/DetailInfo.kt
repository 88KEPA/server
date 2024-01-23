package com.kepa.application.user.dto.response

import Gender
import Role
import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.domain.user.trainer.Trainer
import java.time.LocalDate

data class DetailInfo(
    val name: String,
    val gender: Gender,
    val email: String,
    @JsonFormat(pattern = "yyyy.MM.dd")
    val birth: LocalDate,
    val address: String,
    val addressMeta: String,
    val addressDetail: String,
    val phone: String,
    val role: Role
) {
    companion object {
        fun create(trainer: Trainer) : DetailInfo{
            return DetailInfo(
                name = trainer.name,
                gender =  trainer.gender,
                email = trainer.email,
                birth = trainer.birth,
                address = trainer.address,
                addressMeta = trainer.addressMeta,
                addressDetail = trainer.addressDetail,
                phone = trainer.phone,
                role =  trainer.role
            )
        }
    }
}

