package com.kepa.application.user.trainer

import CertType
import Role
import com.kepa.application.user.RefreshTokenRepository
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.request.MailContent
import com.kepa.application.user.trainer.dto.request.MessageContent
import com.kepa.application.user.trainer.dto.request.AccountJoin
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode.*
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumber
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import com.kepa.token.TokenProvider
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class TrainerWriteService(
    private val accountRepository: AccountRepository,
    private val certNumberRepository: CertNumberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun join(trainerJoin: AccountJoin) {
        require(!trainerJoin.phone.contains("-")) {
            throw KepaException(BAD_REQUEST_PHONE_FORMAT)
        }
        require(
            !accountRepository.existsByEmail(
                trainerJoin.email
            )
        ) {
            throw KepaException(ALREADY_INFORMATION)
        }

        accountRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(trainerJoin.password)))
    }


    fun sendNumber(receiverPhoneNumber: String, email: String, randomNumber: Int, certType: CertType) {
        if(accountRepository.existsByPhone(receiverPhoneNumber)) {
            throw KepaException(ALREADY_INFORMATION)
        }
        require(!receiverPhoneNumber.contains("-")) {
            throw KepaException(BAD_REQUEST_PHONE_FORMAT)
        }
        if(certNumberRepository.existsByReceiverEmailAndCertType(email, certType)) {
            certNumberRepository.deleteByReceiverEmailAndCertType(email,certType)
        }
        certNumberRepository.save(CertNumber(
            number = randomNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            receiverEmail =  email,
            certType = certType))

        applicationEventPublisher.publishEvent(MessageContent(
            certNumber = randomNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            email = email,
        ))
    }

    @Transactional(noRollbackFor = [RuntimeException::class])
    fun checkNumber(receiverPhoneNumber: String, email: String, randomNumber: Int) {
        val certNumber = certNumberRepository.findByReceiverEmailAndReceiverPhoneNumberAndCertType(
            email = email,
            phoneNumber = receiverPhoneNumber,
            certType = CertType.PHONE
        ) ?: throw KepaException(NOT_FOUND_CERT_NUMBER)
        require(certNumber.number == randomNumber) {
            throw KepaException(NOT_MATCH_CERT_NUMBER)
        }
        require(certNumber.createdAt.plusMinutes(3).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(EXPIRE_CERT_NUMBER)
        }
    }

    fun sendMail(receiverEmail:String, randomNumber: Int, certType: CertType) {
        if(certNumberRepository.existsByReceiverEmailAndCertType(receiverEmail, certType)) {
            certNumberRepository.deleteByReceiverEmailAndCertType(receiverEmail,certType)
        }
        certNumberRepository.save(CertNumber(
            number = randomNumber,
            receiverEmail =  receiverEmail,
            certType = certType))

        applicationEventPublisher.publishEvent(MailContent(
            certNumber = randomNumber,
            email = receiverEmail,
        ))
    }


    @Transactional(noRollbackFor = [RuntimeException::class])
    fun checkEmailNumber(email: String, randomNumber: Int) {
        val certNumber = certNumberRepository.findByReceiverEmailAndCertType(
            email = email,
            certType = CertType.EMAIL
        ) ?: throw KepaException(NOT_FOUND_CERT_NUMBER)
        require(certNumber.number == randomNumber) {
            throw KepaException(NOT_MATCH_CERT_NUMBER)
        }
        require(certNumber.createdAt.plusMinutes(3).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(EXPIRE_CERT_NUMBER)
        }
    }

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = accountRepository.findByEmailAndRole(loginInfo.email,loginInfo.role)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        require(bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        }
        if(refreshTokenRepository.existsByEmailAndRole(trainer.email,trainer.role)) {
            refreshTokenRepository.deleteByEmail(trainer.email)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }

    fun logout(loginUserInfo: LoginUserInfo) {
        val trainer = accountRepository.findByIdOrNull(loginUserInfo.id)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        refreshTokenRepository.deleteByEmail(trainer.email)
    }


    fun getToken(id: Long, role: Role, now: Date): LoginToken {
        if(role == Role.TRAINER) {
            val account : Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(NOT_EXSISTS_INFO)
            return tokenProvider.getToken(account.email, account.role.name, now)
        }
        throw KepaException(NOT_EXSISTS_INFO)
    }
}