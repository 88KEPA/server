package com.kepa.application.banner

import com.kepa.application.banner.dto.request.BannerCreate
import com.kepa.application.banner.dto.response.Banners
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

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
@Secured("ROLE_ADMIN","ROLE_SUPER_ADMIN")
@Api(tags = ["[Admin +Banner] 배너"])
@RequestMapping("/api/admin/banner")
@RestController
class BannerAdminController(
    private val bannerWriteService: BannerWriteService,
    private val bannerAdminReadService: BannerAdminReadService,
) {

    @ApiOperation(value = "배너 목록")
    @GetMapping
    fun getAll(request: HttpServletRequest): List<Banners> {
        return bannerAdminReadService.getAll(request)
    }

    @ApiOperation(value = "배너 등록" )
    @Secured("ROLE_ADMIN")
    @PostMapping
    fun create(
        @LoginUser loginUserInfo: LoginUserInfo,
        @RequestPart bannerCreate: BannerCreate,
        @RequestPart src: MultipartFile
    ) {
        bannerWriteService.create(
            title = bannerCreate.title,
            explain = bannerCreate.explain,
            backGroundColor = bannerCreate.backGroundColor,
            isActive = bannerCreate.isActive,
            alt = bannerCreate.alt,
            src = src
        )
    }

    @ApiOperation(value = "배너 상세보기")
    @GetMapping("/{bannerId}")
    fun get(@PathVariable(value = "bannerId") bannerId: Long,
            @LoginUser loginUserInfo: LoginUserInfo,): Banners {
        return bannerAdminReadService.get(bannerId)
    }

    @ApiOperation(value = "배너 삭제")
    @DeleteMapping("/{bannerId}")
    fun delete(@PathVariable(value = "bannerId") bannerId: Long,
               @LoginUser loginUserInfo: LoginUserInfo,) {
        bannerWriteService.delete(bannerId)
    }

    @ApiOperation(value = "활성화 상태 변경")
    @PutMapping("/active/{bannerId}")
    fun updateActive(
        @PathVariable(value = "bannerId") bannerId: Long,
        @LoginUser loginUserInfo: LoginUserInfo,
        @RequestBody isActive: Boolean,
    ) {
        bannerWriteService.updateActive(bannerId = bannerId, isActive = isActive)
    }
    @ApiOperation(value = "배너 수정")
    @PutMapping("/{bannerId}")
    fun update(@PathVariable(value = "bannerId") bannerId: Long,
               @LoginUser loginUserInfo: LoginUserInfo,
               @RequestPart bannerCreate: BannerCreate,
               @RequestPart src: MultipartFile) {
        bannerWriteService.update(
            bannerId = bannerId,
            title = bannerCreate.title,
            explain = bannerCreate.explain,
            backGroundColor = bannerCreate.backGroundColor,
            isActive = bannerCreate.isActive,
            src = src,
            alt = bannerCreate.alt
        )
    }

    @ApiOperation(value = "정렬 순서 수정")
    @PutMapping("/order")
    fun updateOrder(@RequestBody bannerIds: List<Long>) {
        bannerWriteService.updateOrder(bannerIds)
    }
}