package com.kepa.application.banner

import com.kepa.application.banner.dto.response.Banners
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.banner.BannerRepository
import com.kepa.file.s3.S3FileManagement
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * packageName    : com.kepa.application.banner
 * fileName       : BannerReadService
 * author         : hoewoonjeong
 * date           : 3/14/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/14/24        hoewoonjeong               최초 생성
 */
@Transactional(readOnly = true)
@Service
class BannerReadService(
    private val s3FileManagement: S3FileManagement,
    private val bannerRepository: BannerRepository,
) {
    fun getAll(): List<Banners> {
        return bannerRepository.findAllByIsActiveIsTrue().map {
            Banners.of(it, s3FileManagement.getFile(it.image))
        }
    }

    fun get(bannerId: Long): Banners {
        val banner = bannerRepository.findByIdOrNull(bannerId)
            ?: throw KepaException(ExceptionCode.NOT_EXSITS_BANNER)
        return Banners.of(banner, s3FileManagement.getFile(banner.image))
    }
}