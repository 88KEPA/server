package com.kepa.application.user

import Role
import com.kepa.application.user.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun existsByEmailAndRole(email: String, role: Role): Boolean

    fun findByEmailAndRole(email:String, role: Role): RefreshToken?
    fun deleteByEmail(email: String)
}