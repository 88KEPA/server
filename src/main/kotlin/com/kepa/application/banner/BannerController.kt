package com.kepa.application.banner

import com.kepa.application.banner.dto.request.BannerCreate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * packageName    : com.kepa.application.banner
 * fileName       : BannerController
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
@Api(tags = ["[Banner] 배너"])
@RequestMapping("/api/banner")
@RestController
class BannerController(
    private val bannerWriteService: BannerWriteService,
) {
    @ApiOperation(value = "배너 등록")
    @PostMapping
    fun create(@RequestPart bannerCreate: BannerCreate, @RequestPart image: MultipartFile) {
        bannerWriteService.create(
            title = bannerCreate.title,
            explain = bannerCreate.explain,
            backGroundColor = bannerCreate.backGroundColor,
            isActive = bannerCreate.isActive,
            image = image
        )
    }
}