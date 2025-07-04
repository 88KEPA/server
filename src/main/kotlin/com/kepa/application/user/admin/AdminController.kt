package com.kepa.application.user.admin

import com.kepa.application.user.AccountReadService
import com.kepa.application.user.admin.dto.request.FilterRequest
import com.kepa.application.user.admin.dto.request.Resource
import com.kepa.application.user.admin.dto.response.AccountDetailInfo
import com.kepa.application.user.admin.dto.response.ApplyPartnerDetailInfo
import com.kepa.application.user.admin.dto.response.ApplyPartners
import com.kepa.application.user.admin.dto.response.JoinTrainers
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.dto.response.DetailInfo
import com.kepa.application.user.dto.response.PageResponse
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@Api(tags = ["[Admin] 관리자 API"])
@RestController
@RequestMapping("/api/admin")
@Secured(value = ["ROLE_ADMIN", "ROLE_SUPER_ADMIN"])
class AdminController(
    private val accountReadService: AccountReadService,
    private val adminReadService: AdminReadService,
    private val adminWriteService: AdminWriteService,
) {
    @ApiOperation(value = "관리자 정보보기")
    @GetMapping("/info")
    fun getDetailInfo(@LoginUser loginUserInfo: LoginUserInfo): DetailInfo {
        return DetailInfo.create(accountReadService.getDetailInfo(loginUserInfo.id))
    }

    @ApiOperation(value = "트레이너 목록")
    @GetMapping("/list/trainer")
    fun getTrainer(@LoginUser loginUserInfo: LoginUserInfo, filterRequest: FilterRequest): PageResponse<JoinTrainers> {
        return adminReadService.getJoinTrainer(page = filterRequest.page, limit = filterRequest.limit,
            keyword = filterRequest.keyword, sort = filterRequest.sort, isResource = filterRequest.isResource)
    }

    @ApiOperation(value = "회원정보 상세보기")
    @GetMapping("/{id}")
    fun getAccountDetailInfo(@PathVariable(required = true, value = "id") id: Long, @LoginUser loginUserInfo: LoginUserInfo): AccountDetailInfo {
        return adminReadService.getAccountDetailInfo(id)
    }

    @ApiOperation(value = "신청 제휴업체 목록")
    @GetMapping("/partners")
    fun getPartners(@LoginUser loginUserInfo: LoginUserInfo, filterRequest: FilterRequest): PageResponse<ApplyPartners> {
        return adminReadService.getPartners(page = filterRequest.page, limit = filterRequest.limit, filterRequest.keyword)
    }

    @ApiOperation(value = "신청 제휴업체 상세보기")
    @GetMapping("/partners/{id}")
    fun getPartnerDetail(@PathVariable(required = true, value = "id") id: Long, @LoginUser loginUserInfo: LoginUserInfo): ApplyPartnerDetailInfo {
        return adminReadService.getPartnerDetailInfo(id)
    }

    @ApiOperation(value = "인재플 정보 수정")
    @PutMapping("/resource")
    fun updateIsResource(@RequestBody resource: Resource) {
        adminWriteService.updateIsResource(accountId = resource.accountId, isResource = resource.isResource)
    }
}