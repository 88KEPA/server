package com.kepa.application.trainer.dto.request

import com.kepa.common.exception.ExceptionCode.ALREADY_INFORMATION
import com.kepa.common.exception.ExceptionCode.NOT_MATCH_PASSWORD_CONFIRM_PASSWORD
import com.kepa.common.exception.KepaException
import com.kepa.domain.trainer.Trainer
import com.kepa.domain.trainer.Gender
import com.kepa.domain.trainer.LoginType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class TrainerJoin(
    @NotBlank val name: String,
    @NotBlank val loginId: String,
    @NotBlank val password: String,
    @NotBlank val confirmPassword: String,
    @NotBlank val phone: String,
    @NotBlank @Email val email: String,
    @NotBlank val zipCode: String,
    @NotBlank val jibunAddress: String,
    @NotBlank val jibunAddressDetail: String,
    @NotBlank val roadAddress: String,
    @NotBlank val roadAddressDetail: String,
    @NotBlank val birth: LocalDate,
    @NotBlank val gender: Gender,
    @NotBlank val loginType: LoginType,
) {
    fun create(bCryptPasswordEncoder: BCryptPasswordEncoder): Trainer {
        return Trainer(
            name = name,
            loginId = loginId,
            password = bCryptPasswordEncoder.encode(password),
            phone = phone,
            email = email,
            zipCode = zipCode,
            jibunAddress = jibunAddress,
            jibunAddressDetail = jibunAddressDetail,
            roadAddress = roadAddress,
            roadAddressDetail = roadAddressDetail,
            gender = gender,
            loginType = loginType,
            birth = birth
        )
    }

    fun checkPassword() {
        require(confirmPassword == password) {
            throw KepaException(NOT_MATCH_PASSWORD_CONFIRM_PASSWORD)
        }
    }
    fun checkDuplicateInformation(isDuplicate: Boolean) {
        require(!isDuplicate) {
            throw KepaException(ALREADY_INFORMATION)
        }
    }

}
