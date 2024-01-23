package com.kepa.application.user.admin

import com.kepa.application.user.AccountReadService
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.dto.response.DetailInfo
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["[Admin] 관리자 API"])
@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val accountReadService: AccountReadService
) {
    @ApiOperation(value ="관리자 정보보기")
    @GetMapping("/info")
    fun getDetailInfo(@LoginUser loginUserInfo: LoginUserInfo) : DetailInfo {
        return DetailInfo.create(accountReadService.getDetailInfo(loginUserInfo.id))
    }
}