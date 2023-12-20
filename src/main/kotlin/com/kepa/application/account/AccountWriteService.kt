package com.kepa.application.account

import com.kepa.application.account.dto.request.AccountJoin
import com.kepa.domain.account.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountWriteService(
    private val accountRepository: AccountRepository,
) {
    fun join(accountJoin: AccountJoin) {
        accountJoin.checkPassword()
        accountJoin.checkDuplicateInformation(
            accountRepository.existsByLoginIdOrEmail(
                accountJoin.loginId,
                accountJoin.email
            )
        )
        accountRepository.save(accountJoin.create())
    }
}