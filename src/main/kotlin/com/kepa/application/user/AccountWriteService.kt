package com.kepa.application.user

import Role
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
        val trainer = accountRepository.findByEmail(loginInfo.email)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        require(bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        }

        if (refreshTokenRepository.existsByEmailAndRole(trainer.email, trainer.role)) {
            refreshTokenRepository.deleteByEmail(trainer.email)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }

    fun logout(loginUserInfo: LoginUserInfo) {
        val account = accountRepository.findByIdOrNull(loginUserInfo.id)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        refreshTokenRepository.deleteByEmail(account.email)
    }

    fun getToken(id: Long, role: Role, now: Date): LoginToken {
        val account: Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(NOT_EXSISTS_INFO)
        return tokenProvider.getToken(account.email, account.role.name, now)
    }
    fun withdrawAccount(accountId: Long) {
        val account = accountRepository.findByIdOrNull(accountId) ?: throw KepaException(NOT_EXSISTS_INFO)
        refreshTokenRepository.deleteByEmail(account.email)
        agreementTermsRepository.deleteByAccountId(accountId)
        accountRepository.withdrawAccount(accountId)
    }
}