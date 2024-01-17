package com.kepa.application.user.trainer

import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class TrainerReadService(
    private val trainerRepository: TrainerRepository,
    ) {

    fun checkEmail(email: String) {
        if(trainerRepository.existsByEmail(email)) {
            throw KepaException(ExceptionCode.ALREADY_INFORMATION)
        }
    }
}