package com.kepa.application.user

import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api")
class UserController(
    private val userWriteService: UserWriteService,
) {

    @Operation(description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginInfo: LoginInfo): LoginToken = userWriteService.login(
        loginInfo,
        Date()
    )


    @Operation(description = "토큰 재발급")
    @GetMapping("/token")
    fun getToken(loginUserInfo: LoginUserInfo) : LoginToken = userWriteService.getToken(
        loginUserInfo.id, loginUserInfo.role, Date()
    )
}