package com.kepa.application.user

import CertType
import Role
import com.kepa.application.user.RefreshTokenRepository
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.request.MailContent
import com.kepa.application.user.trainer.dto.request.MessageContent
import com.kepa.application.user.trainer.dto.request.AccountJoin
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode.*
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.CertNumber
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import com.kepa.token.TokenProvider
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class AccoutWriteService(
    private val accountRepository: AccountRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = accountRepository.findByEmail(loginInfo.email)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        require(bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        }
        if(refreshTokenRepository.existsByEmailAndRole(trainer.email,trainer.role)) {
            refreshTokenRepository.deleteByEmail(trainer.email)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }

    fun logout(loginUserInfo: LoginUserInfo) {
        val trainer = accountRepository.findByIdOrNull(loginUserInfo.id)
            ?: throw KepaException(NOT_MATCH_ID_OR_PASSWORD)
        refreshTokenRepository.deleteByEmail(trainer.email)
    }


    fun getToken(id: Long, role: Role, now: Date): LoginToken {
        if(role == Role.TRAINER) {
            val account : Account = accountRepository.findByIdOrNull(id) ?: throw KepaException(NOT_EXSISTS_INFO)
            return tokenProvider.getToken(account.email, account.role.name, now)
        }
        throw KepaException(NOT_EXSISTS_INFO)
    }
}