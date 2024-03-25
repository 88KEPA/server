package com.kepa.application.banner

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.banner.Banner
import com.kepa.domain.banner.BannerRepository
import com.kepa.file.s3.S3FileManagement
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
 * packageName    : com.kepa.application.banner
 * fileName       : BannerWriteService
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
@Transactional
@Service
class BannerWriteService(
    private val s3FileManagement: S3FileManagement,
    private val bannerRepository: BannerRepository,
) {
    fun create(
        title: List<String>,
        explain: List<String>,
        backGroundColor: String,
        isActive: Boolean,
        alt: String?,
        src: MultipartFile
    ) {
        val uploadImage = s3FileManagement.uploadImage(src)
        bannerRepository.save(
            Banner(
                title = title,
                explanation = explain,
                backGroundColor = backGroundColor,
                isActive = isActive,
                image = uploadImage,
                orderNum = bannerRepository.count(),
                alt = alt
            )
        )
    }

    fun delete(bannerId: Long) {
        val banner = bannerRepository.findByIdOrNull(bannerId)
            ?: throw KepaException(ExceptionCode.NOT_EXSITS_BANNER)
        s3FileManagement.delete(banner.image)
        bannerRepository.deleteById(bannerId)
    }

    fun updateActive(bannerId: Long, isActive: Boolean) {
        val banner = bannerRepository.findByIdOrNull(bannerId)
            ?: throw KepaException(ExceptionCode.NOT_EXSITS_BANNER)
        banner.isActive = isActive
    }

    fun update(
        bannerId: Long,
        title: List<String>,
        explain: List<String>,
        backGroundColor: String,
        isActive: Boolean,
        src: MultipartFile,
        alt: String?
    ) {
        val banner = bannerRepository.findByIdOrNull(bannerId)
            ?: throw KepaException(ExceptionCode.NOT_EXSITS_BANNER)
        s3FileManagement.delete(banner.image)
        val uploadImage = s3FileManagement.uploadImage(src)
        banner.update(
            title = title,
            explain = explain,
            backGroundColor = backGroundColor,
            isActive = isActive,
            image = uploadImage,
            alt = alt
        )
    }

    fun updateOrder(bannerIds: List<Long>) {
        bannerRepository.findAll().forEach{
            it.orderNum = bannerIds.indexOf(it.id).toLong()
        }
    }
}