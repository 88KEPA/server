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
        val FIRST_INDEX = 0
        val END_INDEX = 4
    }

    fun checkEmail(email: String) {
        if (accountRepository.existsByEmail(email)) {
            throw KepaException(ExceptionCode.ALREADY_INFORMATION)
        }
    }

    fun findEmail(phone: String?, certId: Int?): String {
        if(phone == null || certId == null) {
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
        return trainer.email.masking(FIRST_INDEX, END_INDEX)
    }

    fun getDetailInfo(id: Long) : Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)


    fun String.masking(startIndex: Int, endIndex: Int): String {
        val substringTarget = this.substring(startIndex, endIndex)
        val substringOrigin = this.substring(endIndex)
        return "*".repeat(substringTarget.length) + substringOrigin
    }
}