package com.kepa.domain.user.account

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    fun existsByAccountId(accountId: Long): Boolean

    fun findByAccountId(accountId: Long): RefreshToken?
    fun deleteByAccountId(accountId: Long)

    fun findByAccessToken(accessToken: String): RefreshToken?

    @Query("SELECT r FROM RefreshToken r JOIN FETCH r.account WHERE r.accessToken = :accessToken")
    fun findTokenByLoginInfo(@Param("accessToken") accessToken: String): RefreshToken?
}