package com.kepa.application.user

import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode.NOT_EXSISTS_INFO
import com.kepa.common.exception.ExceptionCode.NOT_MATCH_ID_OR_PASSWORD
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.AgreementTermsRepository
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import com.kepa.domain.user.account.RefreshTokenRepository
import com.kepa.domain.user.enums.Role
import com.kepa.token.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class AccountWriteService(
    private val accountRepository: AccountRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val agreementTermsRepository: AgreementTermsRepository,
) {

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val account = accountRepository.findByEmail(loginInfo.email)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        require(bCryptPasswordEncoder.matches(loginInfo.password, account.password)) {
            throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        }
        if (refreshTokenRepository.existsByAccountId(account.id)) {
            refreshTokenRepository.deleteByAccountId(account.id)
        }
        return tokenProvider.getToken(account.id, account.role.name, now)
    }

    fun logout(loginUserInfo: LoginUserInfo) {
        val account = accountRepository.findByIdOrNull(loginUserInfo.id)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        refreshTokenRepository.deleteByAccountId(account.id)
    }

    fun getToken(id: Long, role: Role, now: Date): LoginToken {
        val account: Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(NOT_EXSISTS_INFO)
        return tokenProvider.getToken(account.id, account.role.name, now)
    }
    fun withdrawAccount(accountId: Long) {
        val account = accountRepository.findByIdOrNull(accountId) ?: throw KepaException(NOT_EXSISTS_INFO)
        refreshTokenRepository.deleteByAccountId(account.id)
        agreementTermsRepository.deleteByAccountId(accountId)
        accountRepository.withdrawAccount(accountId)
    }
}