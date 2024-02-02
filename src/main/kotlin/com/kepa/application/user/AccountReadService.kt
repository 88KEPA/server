package com.kepa.application.user

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AccountReadService(
    private val accountRepository: AccountRepository,
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

    fun findEmail(phoneNumber: String): String {
        val trainer = accountRepository.findByPhone(phoneNumber) ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        return trainer.email.masking(FIRST_INDEX, END_INDEX)
    }

    fun getDetailInfo(id: Long) : Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)




    fun String.masking(startIndex: Int, endIndex: Int): String {
        val substringTarget = this.substring(startIndex, endIndex)
        val substringOrigin = this.substring(endIndex)
        return "*".repeat(substringTarget.length) + substringOrigin
    }
}