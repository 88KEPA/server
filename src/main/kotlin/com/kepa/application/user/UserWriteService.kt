package com.kepa.application.user

import Role
import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.Trainer
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.token.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class UserWriteService(
    private val trainerRepository: TrainerRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val trainerRefreshTokenRepository: TrainerRefreshTokenRepository,
) {

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = trainerRepository.findByEmailAndRole(loginInfo.email,loginInfo.role)
            ?: throw KepaException(ExceptionCode.NOT_MATCH_ID_OR_PASSWORD)
        if (!bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(ExceptionCode.NOT_MATCH_ID_OR_PASSWORD)
        }
        require(!trainerRefreshTokenRepository.existsByEmailAndRole(trainer.email,trainer.role)) {
            trainerRefreshTokenRepository.deleteByEmail(trainer.email)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }

    fun getToken(id: Long, role: Role, now: Date): LoginToken{
        if(role == Role.TRAINER) {
            val trainer : Trainer= trainerRepository.findByIdOrNull(id) ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
            return tokenProvider.getToken(trainer.email, trainer.role.name, now)
        }
        throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
    }
}