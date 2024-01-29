package com.kepa.domain.user.account

import Role
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun existsByEmailAndRole(email: String, role: Role): Boolean

    fun findByEmailAndRole(email:String, role: Role): RefreshToken?
    fun deleteByEmail(email: String)
}