package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.LoginInfo
import com.kepa.application.trainer.dto.request.TrainerJoin
import com.kepa.application.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.token.TokenProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class TrainerWriteService(
    private val trainerRepository: TrainerRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
) {
    fun join(trainerJoin: TrainerJoin) {
        trainerJoin.checkPassword()
        trainerJoin.checkDuplicateInformation(
            trainerRepository.existsByEmail(
                trainerJoin.email
            )
        )
        trainerRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(trainerJoin.password)))
    }

    fun login(loginInfo: LoginInfo, now: Date): LoginToken {
        val trainer = trainerRepository.findByEmail(loginInfo.email)
            ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        if(!bCryptPasswordEncoder.matches(loginInfo.password, trainer.password)) {
            throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        }
        return tokenProvider.getToken(trainer.email, trainer.role.name, now)
    }
}