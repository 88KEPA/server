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
    val image: String,
) {
    companion object {
        fun of(
            banner: Banner,
            s3Image: String,
        ) : Banners {
            return Banners(
                id = banner.id,
                title = banner.title,
                explain = banner.explanation,
                backGroundColor = banner.backGroundColor,
                image = s3Image
            )
        }
    }
}