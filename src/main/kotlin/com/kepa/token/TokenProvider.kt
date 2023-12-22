package com.kepa.token

import com.kepa.application.trainer.dto.response.LoginToken
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import java.security.Key
import java.util.*

class TokenProvider(
    val secretKey: String
) {
    companion object {
        @Value("\${jwt.access-token-expire-time}")
        private lateinit var ACCESS_TOKEN_EXPIRE_TIME: String

        @Value("\${jwt.refresh-token-expire-time}")
        private lateinit var REFRESH_TOKEN_EXPIRE_TIME: String
    }

    val key: Key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    /**
     * accessToken, refreshToken 생성
     */
    fun getToken(authentication: Authentication, now: Date): LoginToken {
        val accessTokenExpire = now.time + ACCESS_TOKEN_EXPIRE_TIME.toLong()
        val refreshTokenExpire = now.time + REFRESH_TOKEN_EXPIRE_TIME.toLong()
        val accessToken = createToken(
            name = authentication.name,
            tokenExpireTime = accessTokenExpire
        )
        val refreshToken = createToken(
            name = authentication.name,
            tokenExpireTime = refreshTokenExpire
        )
        return LoginToken(accessToken = accessToken, refreshToken = refreshToken)
    }

    //토큰 생성
    private fun createToken(name: String, tokenExpireTime: Long): String {
        return Jwts.builder()
            .setSubject(name)
            .setExpiration(Date(tokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}