package com.kepa.application.user.trainer

import CertType
import com.kepa.application.user.trainer.dto.request.MailContent
import com.kepa.application.user.trainer.dto.request.MessageContent
import com.kepa.application.user.trainer.dto.request.TrainerJoin
import com.kepa.common.exception.ExceptionCode.*
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumber
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.trainer.TrainerRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class TrainerWriteService(
    private val trainerRepository: TrainerRepository,
    private val certNumberRepository: CertNumberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun join(trainerJoin: TrainerJoin) {
        require(!trainerJoin.phone.contains("-")) {
            throw KepaException(BAD_REQUEST_PHONE_FORMAT)
        }
        require(
            !trainerRepository.existsByEmail(
                trainerJoin.email
            )
        ) {
            throw KepaException(ALREADY_INFORMATION)
        }

        trainerRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(trainerJoin.password)))
    }


    fun sendNumber(receiverPhoneNumber: String, email: String, randomNumber: Int, certType: CertType) {
        if(trainerRepository.existsByPhone(receiverPhoneNumber)) {
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
}