package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.LoginInfo
import com.kepa.application.trainer.dto.request.TrainerJoin
import com.kepa.application.trainer.dto.response.LoginToken
import io.swagger.annotations.Api
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@Api(tags = ["[TRAINER] 트레이너 입회 API"])
@RequestMapping("/api/trainer")
class TrainerController(
    private val trainerWriteService: TrainerWriteService,
    private val trainerReadService: TrainerReadService,
) {

    @Operation(description = "트레이너 입회")
    @PostMapping
    fun create(@Valid @RequestBody trainerJoin: TrainerJoin) {
        trainerWriteService.join(trainerJoin);
    }
    @Operation(description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginInfo: LoginInfo): LoginToken = trainerWriteService.login(loginInfo,
        Date()
    )

    @Operation(description = "이메일 중복체크")
    @PostMapping("/check/email")
    fun checkEmail(@RequestBody email: String) {
        trainerReadService.checkEmail(email)
    }
}