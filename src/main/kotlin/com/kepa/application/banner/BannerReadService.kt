package com.kepa.application.banner

import com.kepa.application.banner.dto.response.Banners
import com.kepa.application.banner.dto.response.Image
import com.kepa.domain.banner.Banner
import com.kepa.domain.banner.BannerRepository
import com.kepa.file.s3.S3FileManagement
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest

/**
 * packageName    : com.kepa.application.banner
 * fileName       : BannerReadService
 * author         : hoewoonjeong
 * date           : 3/25/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/25/24        hoewoonjeong               최초 생성
 */
@Transactional(readOnly = true)
@Service
class BannerReadService(
    private val s3FileManagement: S3FileManagement,
    private val bannerRepository: BannerRepository,
) {

    fun getAll(request: HttpServletRequest): List<Banners> {
        return toResponse(bannerRepository.findAllByIsActiveIsTrueOrderByOrderNum())
    }
    private fun toResponse(banners: List<Banner>): List<Banners> {
        return banners.map {
            Banners.of(
                it,
                Image(src = s3FileManagement.getFile(it.image), alt = it.alt)
            )
        }
    }
}