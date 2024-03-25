package com.kepa.application.banner

import com.kepa.application.banner.dto.response.Banners
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Api(tags = ["[Service + Banner] 배너"])
@RequestMapping("/api/banner")
@RestController
class BannerController(
    private val bannerReadService: BannerReadService,
) {
    @ApiOperation(value = "배너 목록")
    @GetMapping
    fun getAll(request: HttpServletRequest): List<Banners> {
        return bannerReadService.getAll(request)
    }
}