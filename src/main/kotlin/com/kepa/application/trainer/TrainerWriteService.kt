package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.LoginInfo
import com.kepa.application.trainer.dto.request.MessageContent
import com.kepa.application.trainer.dto.request.TrainerJoin
import com.kepa.application.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode.*
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumber
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.token.TokenProvider
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class TrainerWriteService(
    private val trainerRepository: TrainerRepository,
    private val certNumberRepository: CertNumberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun join(trainerJoin: TrainerJoin) {
        require(
            !trainerRepository.existsByEmail(
                trainerJoin.email
            )
        ) {
            throw KepaException(ALREADY_INFORMATION)
        }

        trainerRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(trainerJoin.password)))
    }

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = trainerRepository.findByEmail(loginInfo.email)
            ?: throw KepaException(NOT_EXSISTS_INFO)
        if (!bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(NOT_EXSISTS_INFO)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }

    fun sendNumber(receiverPhoneNumber: String, email: String, randomNumber: Int) {
        if(certNumberRepository.existsByReceiverEmail(email)) {
            certNumberRepository.deleteByReceiverEmail(email)
        }
        certNumberRepository.save(CertNumber(number = randomNumber, receiverPhoneNumber = receiverPhoneNumber, receiverEmail =  email))
        applicationEventPublisher.publishEvent(MessageContent(
            certNumber = randomNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            email = email,
        ))
    }

    @Transactional(noRollbackFor = [RuntimeException::class])
    fun checkNumber(receiverPhoneNumber: String, email: String, randomNumber: Int) {
        val certNumber = certNumberRepository.findByReceiverEmailAndReceiverPhoneNumber(
            email = email,
            phoneNumber = receiverPhoneNumber
        ) ?: throw KepaException(NOT_FOUND_CERT_NUMBER)
        require(certNumber.number == randomNumber) {
            throw KepaException(NOT_MATCH_CERT_NUMBER)
        }
        require(certNumber.createdAt.plusMinutes(3).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(EXPIRE_CERT_NUMBER)
        }
    }
}