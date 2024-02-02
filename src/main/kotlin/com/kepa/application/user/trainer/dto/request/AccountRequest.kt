package com.kepa.application.user.trainer.dto.request

import CertType
import Gender
import LoginType
import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.common.exception.ExceptionCode.NOT_MATCH_PASSWORD_CONFIRM_PASSWORD
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.account.Account
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@ApiModel(value = "회원가입 요청")
data class AccountJoin(
    @ApiModelProperty(value = "이름")
    @NotBlank val name: String,
    @ApiModelProperty(value = "비밀번호")
    @NotBlank var password: String,
    @ApiModelProperty(value = "비밀번호 재확인")
    @NotBlank val confirmPassword: String,
    @ApiModelProperty(value = "핸드폰 번호")
    @NotBlank val phone: String,
    @ApiModelProperty(value = "이메일")
    @NotBlank @Email val email: String,
    @ApiModelProperty(value = "도로명 또는 지번 주소")
    @NotBlank val address: String,
    @ApiModelProperty(value = "건물명")
    @NotBlank val addressMeta: String,
    @ApiModelProperty(value = "상세주소")
    @NotBlank val addressDetail: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "출생년도")
    @NotBlank val birth: LocalDate,
    @ApiModelProperty(value = "성별")
    @NotBlank val gender: Gender,
    @ApiModelProperty(value = "회원가입 또는 로그인 타입")
    @NotBlank val loginType: LoginType,
) {
    fun create(encodingPassword: String): Account {
        return Account(
            name = name,
            password = encodingPassword,
            phone = phone,
            email = email,
            address = address,
            addressMeta = addressMeta,
            addressDetail = addressDetail,
            gender = gender,
            loginType = loginType,
            birth = birth
        )
    }
    init {
        require(confirmPassword == password) {
            throw KepaException(NOT_MATCH_PASSWORD_CONFIRM_PASSWORD)
        }
    }
}

@ApiModel(value = "로그인 요청")
data class LoginInfo(
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "비밀번호")
    val password: String,
    @ApiModelProperty(value = "로그인 타입")
    val loginType: LoginType,
)

data class MessageContent(
    @ApiModelProperty(value = "인증번호")
    val certNumber: Int,
    @ApiModelProperty(value = "수신자 핸드폰번호")
    val receiverPhoneNumber: String,
    @ApiModelProperty(value = "이메일")
    val email: String? = null,
)

@ApiModel(value = "[핸드폰] 인증번호 발송 요청")
data class SendPhoneCertNumber(
    @ApiModelProperty(value = "수신자 핸드폰번호")
    val receiverPhoneNumber: String,
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "인증 방식")
    val certType: CertType
)

@ApiModel(value = "인증번호 입력 요청")
data class CheckCertNumber(
    @ApiModelProperty(value = "수신자 핸드폰번호")
    val receiverPhoneNumber: String,
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "인증번호")
    val certNumber: Int,
    @ApiModelProperty(value = "인증 방식")
    val certType: CertType
)

@ApiModel(value = "[이메일] 인증번호 발송")
data class SendEmailCertNumber(
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "인증 방식")
    val certType: CertType
)

@ApiModel(value = "[이메일] 인증번호 발송 요청")
data class CheckEmailCertNumber(
    @ApiModelProperty(value = "이메일")
    val email: String,
    @ApiModelProperty(value = "인증번호")
    val certNumber: Int,
)

data class MailContent(
    @ApiModelProperty(value = "인증번호")
    val certNumber: Int,
    @ApiModelProperty(value = "이메일")
    val email: String,
)

data class DuplicateCheckEmail(
    @ApiModelProperty(value = "이메일")
    val email: String,
)

data class PhoneInfo(
    @ApiModelProperty(value = "핸드폰")
    val phone: String,
)

data class ChangePassword(
    @Email
    @NotBlank val email: String,
    @NotBlank val password: String,
)

data class RecoveryCheck(
    @ApiModelProperty(value = "전화번호")
    val phoneNumber: String,
    @ApiModelProperty(value = "인증번호")
    val certNumber: Int,
)

