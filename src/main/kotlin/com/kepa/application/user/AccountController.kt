package com.kepa.application.user

import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@Api(tags = ["[LOGIN] 로그인 관련 API"])
@RequestMapping("/api")
class AccountController(
    private val accountWriteService: AccountWriteService,
) {

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginInfo: LoginInfo): LoginToken = accountWriteService.login(
        loginInfo,
        Date()
    )

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    fun logout(@LoginUser loginUserInfo: LoginUserInfo) {
        accountWriteService.logout(loginUserInfo)
    }


    @ApiOperation(value = "토큰 재발급")
    @GetMapping("/token")
    fun getToken(@LoginUser loginUserInfo: LoginUserInfo) : LoginToken = accountWriteService.getToken(
        loginUserInfo.id, loginUserInfo.role, Date()
    )

    @ApiOperation(value = "회원탈퇴")
    @DeleteMapping("/withdraw")
    fun withdrawAccount(@LoginUser loginUserInfo: LoginUserInfo) {
        accountWriteService.withdrawAccount(loginUserInfo.id)
    }
}