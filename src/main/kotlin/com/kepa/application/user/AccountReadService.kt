package com.kepa.application.user

import com.kepa.application.user.trainer.TrainerCertWriteService
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class AccountReadService(
    private val accountRepository: AccountRepository,
    private val certNumberRepository: CertNumberRepository,
) {
    companion object {
        val START_INDEX = 0;
        val LIMIT_MAX_LENGTH = 4;
    }

    fun checkEmail(email: String) {
        if (accountRepository.existsByEmail(email)) {
            throw KepaException(ExceptionCode.ALREADY_INFORMATION)
        }
    }

    fun findEmail(phone: String?, certId: Int?): String {
        if (phone == null || certId == null) {
            throw KepaException(ExceptionCode.BAD_REQUEST)
        }
        val certNumber = certNumberRepository.findByNumberAndReceiverPhoneNumberAndCertType(certType = CertType.FIND_RESULT, number = certId, phoneNumber = phone)
            ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)

        require(certNumber.createdAt.plusMinutes(TrainerCertWriteService.CHECKT_EXPIRE_TIME).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(ExceptionCode.EXPIRE_CERT_NUMBER)
        }
        certNumberRepository.deleteById(certNumber.id)

        val trainer = accountRepository.findByPhone(phone) ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        return trainer.email.maskEmail()
    }

    fun getDetailInfo(id: Long): Account = accountRepository.findByIdOrNull(id)
        ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)


    /**
     * 앞에 4
     * 최대 4
     * 보여야되는게 최대 4개
     * */
    fun String.maskEmail(): String {
        val expressEmailIndex = this.indexOf('@')
        val emailAddress = this.substring(expressEmailIndex)
        val maskTargetEmailAddress = this.substring(START_INDEX, expressEmailIndex)
        val divideValue = maskTargetEmailAddress.length / LIMIT_MAX_LENGTH
        val restValue = maskTargetEmailAddress.length % LIMIT_MAX_LENGTH
        if (divideValue > 2) {
            return maskTargetEmailAddress.substring(START_INDEX, LIMIT_MAX_LENGTH) +
                "*".repeat(maskTargetEmailAddress.substring(LIMIT_MAX_LENGTH).length) + emailAddress
        } else if (divideValue == 1 && restValue > 0) {
            return maskTargetEmailAddress.substring(START_INDEX, 3) +
                "*".repeat(maskTargetEmailAddress.substring(3).length) + emailAddress
        } else {
            val length = maskTargetEmailAddress.length / 2
            return maskTargetEmailAddress.substring(START_INDEX, length) + "*".repeat(length) + emailAddress
        }
    }
}