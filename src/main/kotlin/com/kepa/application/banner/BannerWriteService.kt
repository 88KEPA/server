package com.kepa.application.banner

import com.kepa.domain.banner.Banner
import com.kepa.domain.banner.BannerRepository
import com.kepa.file.s3.S3FileManagement
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
        image: MultipartFile
    ) {
        val uploadImage = s3FileManagement.uploadImage(image)
        bannerRepository.save(Banner(
            title = title,
            explanation = explain,
            backGroundColor = backGroundColor,
            isActive = isActive
        ))
    }
}