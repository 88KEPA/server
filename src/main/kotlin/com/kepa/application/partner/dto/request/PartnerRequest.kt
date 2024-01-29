package com.kepa.application.partner.dto.request

import com.kepa.domain.partner.Partner
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class PartnerCreate(
    @ApiModelProperty(value = "성함")
    @NotBlank val name: String,
    @ApiModelProperty(value = "조직")
    @NotBlank val organization : String,
    @ApiModelProperty(value = "이메일")
    @Email
    @NotBlank val email: String,
    @ApiModelProperty(value = "핸드폰 번호")
    @NotBlank val phone: String,
    @ApiModelProperty(value = "상담시 참고사항")
    @NotBlank val content: String,
) {
    fun createPartner() : Partner {
        return Partner(
            name = name,
            organization = organization,
            email = email,
            phone =  phone,
            content = content
        )
    }
}