package com.kepa.application.user.admin.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.domain.partner.Partner
import com.kepa.domain.partner.enums.ApproveStatus
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.enums.Gender
import com.kepa.domain.user.enums.LoginType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.LocalDateTime

@ApiModel("가입한 트레이너 정보")
data class JoinTrainers(
    @ApiModelProperty("사용자 id")
    val id: Long,
    @ApiModelProperty("이름")
    val name: String,
    @ApiModelProperty("생년월일")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birth: LocalDate,
    @ApiModelProperty("가입일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @ApiModelProperty("인재풀 여부")
    val isResource: Boolean = false,
)

data class AccountDetailInfo(
    @ApiModelProperty("사용자 id")
    val id: Long,
    @ApiModelProperty("이름")
    val name: String,
    @ApiModelProperty("전화번호")
    val phone: String,
    @ApiModelProperty("이메일")
    val email: String,
    @ApiModelProperty("생년월일")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birth: LocalDate,
    @ApiModelProperty("성별")
    val gender: Gender,
    @ApiModelProperty("로그인 타입")
    val loginType: LoginType,
    @ApiModelProperty("가입일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @ApiModelProperty("주소")
    val address: String,
    @ApiModelProperty("부가정보")
    val addressMeta: String,
    @ApiModelProperty("상세주소")
    val addressDetail: String,
    val isResource: Boolean,
) {
    companion object {
        fun toResponse(account: Account): AccountDetailInfo {
            return AccountDetailInfo(
                id = account.id,
                name = account.name,
                phone = account.phone,
                email = account.email,
                birth = account.birth,
                gender = account.gender,
                loginType = account.loginType,
                createdAt = account.createdAt,
                address = account.address,
                addressMeta = account.addressMeta,
                addressDetail = account.addressDetail,
                isResource = account.isResource
            )
        }
    }
}

@ApiModel("제휴신청한 업체 정보")
data class ApplyPartners(
    @ApiModelProperty("신청한 제휴업체 id")
    val id: Long,
    @ApiModelProperty(value = "승인상태")
    val approveStatus: ApproveStatus,
    @ApiModelProperty(value = "단체명")
    val organization: String,
    @ApiModelProperty(value = "신청일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
)

data class ApplyPartnerDetailInfo(
    @ApiModelProperty("사용자 id")
    val id: Long,
    @ApiModelProperty(value = "승인상태")
    val approveStatus: ApproveStatus,
    @ApiModelProperty(value = "단체명")
    val organization: String,
    @ApiModelProperty(value = "직책")
    val position: String,
    @ApiModelProperty(value = "연락처")
    val phone: String,
    @ApiModelProperty(value = "신청일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun toResponse(partner: Partner): ApplyPartnerDetailInfo {
            return ApplyPartnerDetailInfo(
                id = partner.id,
                approveStatus = partner.approveStatus,
                organization = partner.organization,
                position = partner.position,
                phone = partner.phone,
                createdAt = partner.createdAt,
            )
        }
    }
}
