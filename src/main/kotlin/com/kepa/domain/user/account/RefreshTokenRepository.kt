package com.kepa.domain.user.account

import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun existsByAccountId(accountId: Long): Boolean

    fun findByAccountId(accountId: Long): RefreshToken?
    fun deleteByAccountId(accountId: Long)

    fun findByAccessToken(accessToken: String): RefreshToken?
}