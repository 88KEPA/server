package com.kepa.application.user

import com.kepa.application.user.trainer.dto.request.LoginInfo
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.token.TokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserWriteService(
    private val trainerRepository: TrainerRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
) {

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = trainerRepository.findByEmailAndRole(loginInfo.email,loginInfo.role)
            ?: throw KepaException(ExceptionCode.NOT_MATCH_ID_OR_PASSWORD)
        if (!bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(ExceptionCode.NOT_MATCH_ID_OR_PASSWORD)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }
}