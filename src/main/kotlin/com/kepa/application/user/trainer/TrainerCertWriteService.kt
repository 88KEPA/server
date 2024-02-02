package com.kepa.application.user.trainer

import CertType
import com.kepa.application.user.trainer.dto.request.MailContent
import com.kepa.application.user.trainer.dto.request.MessageContent
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumber
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.account.AccountRepository
import com.kepa.externalapi.dto.RandomNumber
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Random

@Transactional
@Service
class TrainerCertWriteService(
    val accountRepository: AccountRepository,
    val certNumberRepository: CertNumberRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    companion object {
        val CHECKT_EXPIRE_TIME: Long = 3
    }
    fun sendNumber(receiverPhoneNumber: String, email: String, randomNumber: Int, certType: CertType) {
        if (certType == CertType.FIND) {
            if (!accountRepository.existsByPhone(receiverPhoneNumber)) {
                throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
            }
        } else {
            if (accountRepository.existsByPhone(receiverPhoneNumber)) {
                throw KepaException(ExceptionCode.ALREADY_INFORMATION)
            }
        }
        require(!receiverPhoneNumber.contains("-")) {
            throw KepaException(ExceptionCode.BAD_REQUEST_PHONE_FORMAT)
        }
        if (certNumberRepository.existsByReceiverEmailAndCertType(email, certType)) {
            certNumberRepository.deleteByReceiverEmailAndCertType(email, certType)
        }
        certNumberRepository.save(CertNumber(
            number = randomNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            receiverEmail = email,
            certType = certType))

        applicationEventPublisher.publishEvent(MessageContent(
            certNumber = randomNumber,
            receiverPhoneNumber = receiverPhoneNumber,
            email = email,
        ))
    }

    @Transactional(noRollbackFor = [RuntimeException::class])
    fun checkNumber(receiverPhoneNumber: String, email: String, randomNumber: Int, certType: CertType): Int {
        val certNumber = certNumberRepository.findByReceiverEmailAndReceiverPhoneNumberAndCertType(
            email = email,
            phoneNumber = receiverPhoneNumber,
            certType = certType
        ) ?: throw KepaException(ExceptionCode.NOT_FOUND_CERT_NUMBER)
        require(certNumber.createdAt.plusMinutes(CHECKT_EXPIRE_TIME).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(ExceptionCode.EXPIRE_CERT_NUMBER)
        }

        require(certNumber.number == randomNumber) {
            throw KepaException(ExceptionCode.NOT_MATCH_CERT_NUMBER)
        }
        certNumberRepository.deleteById(certNumber.id)
        val randomNumber = RandomNumber.create()
        certNumberRepository.save(CertNumber(receiverEmail = email, number = randomNumber, certType = CertType.FIND_RESULT))
        return randomNumber
    }

    fun sendMail(receiverEmail: String, randomNumber: Int, certType: CertType) {
        if (certNumberRepository.existsByReceiverEmailAndCertType(receiverEmail, certType)) {
            certNumberRepository.deleteByReceiverEmailAndCertType(receiverEmail, certType)
        }
        certNumberRepository.save(CertNumber(
            number = randomNumber,
            receiverEmail = receiverEmail,
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
        ) ?: throw KepaException(ExceptionCode.NOT_FOUND_CERT_NUMBER)
        require(certNumber.createdAt.plusMinutes(CHECKT_EXPIRE_TIME).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(ExceptionCode.EXPIRE_CERT_NUMBER)
        }

        require(certNumber.number == randomNumber) {
            throw KepaException(ExceptionCode.NOT_MATCH_CERT_NUMBER)
        }
        certNumberRepository.deleteById(certNumber.id)
    }

    fun recoverySend(phoneNumber: String, randomNumber: Int) {
        if (!accountRepository.existsByPhone(phoneNumber)) {
            throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        }
        require(!phoneNumber.contains("-")) {
            throw KepaException(ExceptionCode.BAD_REQUEST_PHONE_FORMAT)
        }
        if (certNumberRepository.existsByReceiverPhoneNumberAndCertType(phoneNumber, CertType.FIND)) {
            certNumberRepository.deleteByReceiverPhoneNumberAndCertType(phoneNumber, CertType.FIND)
        }
        certNumberRepository.save(CertNumber(
            number = randomNumber,
            receiverPhoneNumber = phoneNumber,
            certType = CertType.FIND))

        applicationEventPublisher.publishEvent(MessageContent(
            certNumber = randomNumber,
            receiverPhoneNumber = phoneNumber
        ))
    }

    @Transactional(noRollbackFor = [RuntimeException::class])
    fun recoveryCheck(phoneNumber: String, certNumber: Int, certType: CertType): Int{
        val findCert = certNumberRepository.findByReceiverPhoneNumberAndCertType(phoneNumber, certType)
            ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        require(findCert.createdAt.plusMinutes(CHECKT_EXPIRE_TIME).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(findCert.id)
            throw KepaException(ExceptionCode.EXPIRE_CERT_NUMBER)
        }
        require(findCert.number == certNumber) {
            throw KepaException(ExceptionCode.NOT_MATCH_CERT_NUMBER)
        }
        certNumberRepository.deleteById(findCert.id)
        val randomNumber = RandomNumber.create()
        certNumberRepository.save(CertNumber(receiverPhoneNumber = phoneNumber, number = randomNumber, certType = CertType.FIND_RESULT))
        return randomNumber
    }
}
