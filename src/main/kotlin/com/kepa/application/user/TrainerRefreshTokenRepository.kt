package com.kepa.application.user

import Role
import com.kepa.application.user.domain.TrainerRefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface TrainerRefreshTokenRepository : JpaRepository<TrainerRefreshToken, Long> {

    fun existsByEmailAndRole(email: String, role: Role): Boolean

    fun findByEmailAndRole(email:String, role: Role): TrainerRefreshToken?
    fun deleteByEmail(email: String)
}