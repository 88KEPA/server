package com.kepa.domain.trainer

import org.springframework.data.jpa.repository.JpaRepository

interface TrainerRepository : JpaRepository<Trainer,Long> {
    fun existsByLoginIdOrEmail(loginId: String, email: String) : Boolean
}