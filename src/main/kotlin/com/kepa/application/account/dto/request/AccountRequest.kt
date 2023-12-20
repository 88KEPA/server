package com.kepa.application.account.dto.request

import com.kepa.common.exception.ExceptionCode.ALREADY_INFORMATION
import com.kepa.common.exception.ExceptionCode.NOT_MATCH_PASSWORD_CONFIRM_PASSWORD
import com.kepa.common.exception.KepaException
import com.kepa.domain.account.Account
import com.kepa.domain.account.Gender
import com.kepa.domain.account.LoginType
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class AccountJoin(
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
    fun create(): Account {
        return Account(
            name = name,
            loginId = loginId,
            password = password,
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
