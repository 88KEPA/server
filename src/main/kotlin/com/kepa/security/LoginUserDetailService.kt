package com.kepa.security

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class LoginUserDetailService(
    private val trainerRepository: TrainerRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        println("=======loadUserByUsername=======")
        val trainer = trainerRepository.findByEmail(username) ?: throw KepaException(
            ExceptionCode.NOT_EXSISTS_INFO
        )
        return LoginUserDetail(trainer.email, trainer.role.name)
    }

}