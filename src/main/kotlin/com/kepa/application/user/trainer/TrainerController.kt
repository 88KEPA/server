package com.kepa.application.user.trainer

import CertType
import com.kepa.application.user.dto.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.*
import com.kepa.domain.user.annotation.LoginUser
import com.kepa.externalapi.dto.RandomNumber
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
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
    @ApiOperation(value = "트레이너 입회")
    @PostMapping
    fun create(@Valid @RequestBody trainerJoin: TrainerJoin) {
        trainerWriteService.join(trainerJoin);
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 409, message = "errorMessage: 이미 가입된 정보입니다. / identity : 40901",)
    )
    @Operation(description = "이메일 중복체크")
    @PostMapping("/check/email")
    fun checkEmail(@RequestBody duplicateCheckEmail: DuplicateCheckEmail) {
        trainerReadService.checkEmail(duplicateCheckEmail.email)
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
    )
    @Operation(description = "인증번호 발송")
    @PostMapping("/send/number")
    fun sendNumber(@RequestBody sendPhoneCertNumber: SendPhoneCertNumber) {
        trainerWriteService.sendNumber(
            receiverPhoneNumber = sendPhoneCertNumber.receiverPhoneNumber,
            email = sendPhoneCertNumber.email,
            randomNumber = RandomNumber.create(),
            certType =  sendPhoneCertNumber.certType
        )
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 400, message = "errorMessage: 인증번호가 일치하지 않습니다. / identity : 40004"),
        ApiResponse(code = 400, message = "errorMessage: 유효시간이 지났습니다. / identity : 40005"),
    )
    @Operation(description = "인증번호 체크")
    @PostMapping("/check/number")
    fun checkNumber(@Valid @RequestBody checkCertNumber: CheckCertNumber) {
        trainerWriteService.checkNumber(receiverPhoneNumber = checkCertNumber.receiverPhoneNumber,
            email = checkCertNumber.email,
            randomNumber = checkCertNumber.certNumber)
    }


    @ApiOperation(value = "이메일 인증번호 발송")
    @PostMapping("/send/email")
    fun sendEmail(@RequestBody sendEmailCertNumber: SendEmailCertNumber) {
        trainerWriteService.sendMail(sendEmailCertNumber.email,RandomNumber.create(),CertType.EMAIL)
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 400, message = "errorMessage: 인증번호가 일치하지 않습니다. / identity : 40004"),
        ApiResponse(code = 400, message = "errorMessage: 유효시간이 지났습니다. / identity : 40005"),
    )
    @ApiOperation(value = "이메일 인증번호 체크")
    @PostMapping("/check/email/number")
    fun checkEmail(@RequestBody checkEmailCertNumber: CheckEmailCertNumber) {
        trainerWriteService.checkEmailNumber(email = checkEmailCertNumber.email, randomNumber = checkEmailCertNumber.certNumber)
    }

    @ApiOperation(value = "로그인한 사용자 체크")
    @GetMapping("/info")
    fun test(@LoginUser loginUserInfo: LoginUserInfo) : LoginUserInfo {
        return loginUserInfo
    }

    @ApiOperation(value = "이메일 찾기")
    @PostMapping("/find/email")
    fun findEmail(@RequestBody phoneNumber: DuplicateCheckPhone) = trainerReadService.findEmail(phoneNumber.phone)
}