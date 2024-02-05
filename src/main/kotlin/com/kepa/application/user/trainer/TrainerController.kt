package com.kepa.application.user.trainer

import CertType
import com.kepa.application.user.AccountReadService
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.dto.response.DetailInfo
import com.kepa.application.user.trainer.dto.request.*
import com.kepa.domain.user.annotation.LoginUser
import com.kepa.externalapi.dto.RandomNumber
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@Api(tags = ["[TRAINER] 트레이너 입회 API"])
@RequestMapping("/api/trainer")
class TrainerController(
    private val trainerWriteService: TrainerWriteService,
    private val accountReadService: AccountReadService,
    private val trainerCertWriteService : TrainerCertWriteService,
    ) {

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 409, message = "errorMessage: 이미 가입된 정보입니다. / identity : 40901")
    )
    @ApiOperation(value = "트레이너 입회")
    @PostMapping
    fun create(@RequestBody accountJoin: AccountJoin) {
        println("LocalDateTime.now() = ${LocalDateTime.now()}")
        trainerWriteService.join(accountJoin);
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 409, message = "errorMessage: 이미 가입된 정보입니다. / identity : 40901")
    )
    @ApiOperation(value = "이메일 중복체크")
    @PostMapping("/check/email")
    fun checkEmail(@RequestBody duplicateCheckEmail: DuplicateCheckEmail) {
        accountReadService.checkEmail(duplicateCheckEmail.email)
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
    )
    @ApiOperation(value = "비밀번호 찾기(FIND), 회원가입(PHONE) 인증번호 발송")
    @PostMapping("/send/cert-number")
    fun sendNumber(@RequestBody sendPhoneCertNumber: SendPhoneCertNumber) {
        trainerCertWriteService.sendNumber(
            receiverPhoneNumber = sendPhoneCertNumber.receiverPhoneNumber,
            email = sendPhoneCertNumber.email,
            randomNumber = RandomNumber.create(),
            certType = sendPhoneCertNumber.certType
        )
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 400, message = "errorMessage: 인증번호가 일치하지 않습니다. / identity : 40004"),
        ApiResponse(code = 400, message = "errorMessage: 유효시간이 지났습니다. / identity : 40005"),
    )
    @ApiOperation(value = "비밀번호 찾기(FIND), 회원가입(PHONE)인증번호 체크")
    @PostMapping("/check/cert-number")
    fun checkNumber(@Valid @RequestBody checkCertNumber: CheckCertNumber): Int {
      return trainerCertWriteService.checkNumber(receiverPhoneNumber = checkCertNumber.receiverPhoneNumber,
            email = checkCertNumber.email,
            randomNumber = checkCertNumber.certNumber,
            certType = checkCertNumber.certType)
    }


    @ApiOperation(value = "이메일 인증번호 발송")
    @PostMapping("/send/email")
    fun sendEmail(@RequestBody sendEmailCertNumber: SendEmailCertNumber) {
        trainerCertWriteService.sendMail(sendEmailCertNumber.email, RandomNumber.create(), CertType.EMAIL)
    }

    @ApiResponses(
        ApiResponse(code = 200, message = ""),
        ApiResponse(code = 400, message = "errorMessage: 인증번호가 일치하지 않습니다. / identity : 40004"),
        ApiResponse(code = 400, message = "errorMessage: 유효시간이 지났습니다. / identity : 40005"),
    )
    @ApiOperation(value = "이메일 인증번호 체크")
    @PostMapping("/check/email/cert-number")
    fun checkEmail(@RequestBody checkEmailCertNumber: CheckEmailCertNumber) {
        trainerCertWriteService.checkEmailNumber(email = checkEmailCertNumber.email, randomNumber = checkEmailCertNumber.certNumber)
    }

    @ApiOperation(value = "사용자 정보 상세보기")
    @GetMapping("/info")
    fun getDetailInfo(@LoginUser loginUserInfo: LoginUserInfo): DetailInfo {
        return DetailInfo.create(accountReadService.getDetailInfo(id = loginUserInfo.id))
    }

    @ApiOperation(value = "이메일 찾기")
    @PostMapping("/find/email")
    fun findEmail(@RequestBody findEmail: FindEmail) = accountReadService.findEmail(phone = findEmail.phone, certId = findEmail.certId)

    @ApiOperation(value = "이메일 찾기 인증번호 발송")
    @PostMapping("/recovery/send/cert-number")
    fun recoverySendEmail(@RequestBody phoneInfo: PhoneInfo) {
        trainerCertWriteService.recoverySend(phoneInfo.phone, RandomNumber.create())
    }

    @ApiOperation(value = "이메일 찾기 인증번호 체크")
    @PostMapping("/recovery/check")
    fun recoveryCheck(@RequestBody recoveryCheck: RecoveryCheck): Int {
        return trainerCertWriteService.recoveryCheck(recoveryCheck.phone, recoveryCheck.certNumber, CertType.FIND)
    }

    @ApiOperation(value = "비밀번호 변경")
    @PostMapping("/change/password")
    fun changePassword(@RequestBody changePassword: ChangePassword) {
        trainerWriteService.changePassword(certId = changePassword.certId,email= changePassword.email, password = changePassword.password)
    }
}