package com.kepa.application.banner.dto.response

import com.kepa.domain.banner.Banner

/**
 * packageName    : com.kepa.application.banner.dto.response
 * fileName       : BannerResponse
 * author         : hoewoonjeong
 * date           : 3/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        hoewoonjeong               최초 생성
 */

data class Banners(
    val id: Long,
    val title: List<String>,
    val explain: List<String>,
    val backGroundColor: String,
    val image: Image,
    val orderNum: Long,
    val isActive: Boolean,
    ) {
    companion object {
        fun of(
            banner: Banner,
            s3Image: Image,
        ): Banners {
            return Banners(
                id = banner.id,
                title = banner.title,
                explain = banner.explanation,
                backGroundColor = banner.backGroundColor,
                image = s3Image,
                orderNum = banner.orderNum,
                isActive = banner.isActive
            )
        }
    }
}

data class Image(
    val src: String,
    val alt: String?,
)