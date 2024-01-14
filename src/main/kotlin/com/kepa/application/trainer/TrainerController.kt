package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.CheckCertNumber
import com.kepa.application.trainer.dto.request.LoginInfo
import com.kepa.application.trainer.dto.request.SendCertNumber
import com.kepa.application.trainer.dto.request.TrainerJoin
import com.kepa.application.trainer.dto.response.LoginToken
import com.kepa.externalapi.dto.RandomNumber
import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
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

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 409, message = "errorMessage: 이미 가입된 정보입니다. / identity : 40901",)
    )
    @Operation(description = "트레이너 입회")
    @PostMapping
    fun create(@Valid @RequestBody trainerJoin: TrainerJoin) {
        trainerWriteService.join(trainerJoin);
    }
    @Operation(description = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody loginInfo: LoginInfo): LoginToken = trainerWriteService.login(
        loginInfo,
        Date()
    )


    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 409, message = "errorMessage: 이미 가입된 정보입니다. / identity : 40901",)
    )
    @Operation(description = "이메일 중복체크")
    @PostMapping("/check/email")
    fun checkEmail(@RequestBody email: String) {
        trainerReadService.checkEmail(email)
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
    )
    @Operation(description = "인증번호 발송")
    @PostMapping("/send/number")
    fun sendNumber(@RequestBody sendCertNumber: SendCertNumber) {
        trainerWriteService.sendNumber(
            receiverPhoneNumber = sendCertNumber.receiverPhoneNumber,
            email = sendCertNumber.email,
            randomNumber = RandomNumber.create()
        )
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 400, message = "errorMessage: 인증번호가 일치하지 않습니다. / identity : 40004"),
        ApiResponse(code = 404, message = "errorMessage: 인증번호가 존재하지 않습니다. / identity : 40401"),
    )
    @Operation(description = "인증번호 체크")
    @PostMapping("/check/number")
    fun checkNumber(@RequestBody checkCertNumber: CheckCertNumber) {
        trainerWriteService.checkNumber(receiverPhoneNumber = checkCertNumber.receiverPhoneNumber,
            email = checkCertNumber.email,
            randomNumber = checkCertNumber.certNumber)
    }

}