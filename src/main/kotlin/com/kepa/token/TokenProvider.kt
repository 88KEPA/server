package com.kepa.token

import Role
import com.kepa.domain.user.account.RefreshTokenRepository
import com.kepa.domain.user.account.RefreshToken
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.account.AccountRepository
import com.kepa.security.LoginUserDetail
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(
    private val accountRepository: AccountRepository,
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.access-token-expire-time}")
    private val ACCESS_TOKEN_EXPIRE_TIME: String,
    @Value("\${jwt.refresh-token-expire-time}")
    private val REFRESH_TOKEN_EXPIRE_TIME: String,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    val key: Key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    /**
     * accessToken, refreshToken 생성
     */
    fun getToken(id: Long, role: String, now: Date): LoginToken {
        val now: LocalDateTime = LocalDateTime.now()
        val nowTokenExpire = now.toInstant(ZoneOffset.UTC).toEpochMilli()
        val accessTokenTime: LocalDateTime = now.plus(ACCESS_TOKEN_EXPIRE_TIME.toLong(), ChronoUnit.SECONDS)
        val refreshTokenTime: LocalDateTime = now.plus(REFRESH_TOKEN_EXPIRE_TIME.toLong(), ChronoUnit.SECONDS)

        val accessTokenExpire = accessTokenTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        val refreshTokenExpire = refreshTokenTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        val accessToken = createToken(
            id = id,
            tokenExpireTime = accessTokenExpire,
            role = role
        )
        val loginUserRole = Role.valueOf(role)
        var loginToken: LoginToken? = refreshTokenRepository.findByAccountId(id)?.let {
            if (nowTokenExpire > it.expireAt) {
                throw KepaException(ExceptionCode.REFRESH_TOKEN_EXPIRE)
            }
            val account = accountRepository.findByIdOrNull(it.id)
                ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
            LoginToken(accessToken = accessToken, refreshToken = it.token, accessTokenExpiredAt = Date(accessTokenExpire), refreshTokenExpiredAt = Date(it.expireAt), id = account.id)
        }
        if (loginToken == null) {
            val refreshToken = createToken(
                id = id,
                tokenExpireTime = refreshTokenExpire,
                role = role
            )
            val account = accountRepository.findByIdOrNull(id)
                ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
            val savedRefreshToken = refreshTokenRepository.save(RefreshToken(account = account, token = refreshToken, expireAt = refreshTokenExpire, role = loginUserRole))
            loginToken = LoginToken(accessToken = accessToken, refreshToken = refreshToken, accessTokenExpiredAt = Date(accessTokenExpire), refreshTokenExpiredAt = Date(refreshTokenExpire), id = savedRefreshToken.id)
        }
        return loginToken
    }

    fun getAuthentication(token: String): Authentication {
        val account = accountRepository.findByIdOrNull(getAccount(token)) ?: throw KepaException(
            ExceptionCode.NOT_EXSISTS_INFO
        )
        val loginUserDetail = LoginUserDetail(account.email, account.role.name);
        return UsernamePasswordAuthenticationToken(loginUserDetail, "", loginUserDetail.authorities)
    }

    fun getAccount(token: String): Long {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject.toLong()
    }


    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            return true;
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: ExpiredJwtException) {
            return false
        } catch (e: UnsupportedJwtException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest?): String? {
        return request?.getHeader("Authorization")
    }

    //토큰 생성
    private fun createToken(id: Long, tokenExpireTime: Long, role: String): String {
        return Jwts.builder()
            .setSubject(id.toString())
            .claim("auth", role)
            .setExpiration(Date(tokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}