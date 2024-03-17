package com.kepa.application.banner.dto.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * packageName    : com.kepa.application.banner.dto.request
 * fileName       : BannerRequest
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
@ApiModel(value = "배너 등록")
data class BannerCreate(
    @ApiModelProperty("제목")
    val title: List<String>,
    @ApiModelProperty("설명")
    val explain: List<String>,
    @ApiModelProperty("배경색")
    val backGroundColor: String,
    @ApiModelProperty("활성화 여부")
    val isActive: Boolean,
    @ApiModelProperty("이미지 부가 설명")
    val alt: String?,
)