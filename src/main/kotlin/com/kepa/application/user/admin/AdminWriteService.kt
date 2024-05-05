package com.kepa.application.user.admin

import com.kepa.common.exception.ExceptionCode.NOT_EXSISTS_INFO
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.account.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AdminWriteService(
    private val accountRepository: AccountRepository,
) {

    fun updateIsResource(accountId: Long ,isResource: Boolean) {
        val account = accountRepository.findByIdOrNull(accountId) ?: throw KepaException(NOT_EXSISTS_INFO)
        account.updateIsResource(isResource)
    }
}