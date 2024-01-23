package com.kepa.application.user

import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class AccountController(
    private val accountWriteService: AccoutWriteService,
) {

    @Operation(description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginInfo: LoginInfo): LoginToken = accountWriteService.login(
        loginInfo,
        Date()
    )

    @Operation(description = "로그아웃")
    @PostMapping("/logout")
    fun logout(@LoginUser loginUserInfo: LoginUserInfo) {
        accountWriteService.logout(loginUserInfo)
    }


    @Operation(description = "토큰 재발급")
    @GetMapping("/token")
    fun getToken(@LoginUser loginUserInfo: LoginUserInfo) : LoginToken = accountWriteService.getToken(
        loginUserInfo.id, loginUserInfo.role, Date()
    )
}