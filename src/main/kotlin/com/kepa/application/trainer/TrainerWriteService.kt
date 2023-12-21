package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.TrainerJoin
import com.kepa.domain.trainer.TrainerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TrainerWriteService(
    private val trainerRepository: TrainerRepository,
) {
    fun join(trainerJoin: TrainerJoin) {
        trainerJoin.checkPassword()
        trainerJoin.checkDuplicateInformation(
            trainerRepository.existsByLoginIdOrEmail(
                trainerJoin.loginId,
                trainerJoin.email
            )
        )
        trainerRepository.save(trainerJoin.create())
    }
}