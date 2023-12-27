package com.kepa.domain.user.trainer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface TrainerRepository : JpaRepository<Trainer,Long> {
    fun existsByEmail(email: String) : Boolean

    fun findByEmail(@Param("email")email: String?) : Trainer?
}