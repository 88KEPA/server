package com.kepa.domain.user.trainer

import Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface TrainerRepository : JpaRepository<Trainer,Long> {
    fun existsByEmail(email: String) : Boolean

    fun findByEmailAndRole(@Param("email")email: String?, @Param("role")role: Role) : Trainer?
}