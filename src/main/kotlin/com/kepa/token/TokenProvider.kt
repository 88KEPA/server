package com.kepa.token

import Role
import com.kepa.application.user.trainer.dto.response.LoginToken
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.trainer.TrainerRepository
import com.kepa.security.LoginUserDetail
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
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
    private val trainerRepository: TrainerRepository,
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.access-token-expire-time}")
    private val ACCESS_TOKEN_EXPIRE_TIME: String,
    @Value("\${jwt.refresh-token-expire-time}")
    private val REFRESH_TOKEN_EXPIRE_TIME: String,
) {

    val key: Key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }
    /**
     * accessToken, refreshToken 생성
     */
    fun getToken(email: String, role: String, now: Date): LoginToken {
        val now: LocalDateTime = LocalDateTime.now()
        val accessTokenTime: LocalDateTime = now.plus(ACCESS_TOKEN_EXPIRE_TIME.toLong(), ChronoUnit.SECONDS)
        val refreshTokenTime: LocalDateTime = now.plus(REFRESH_TOKEN_EXPIRE_TIME.toLong(), ChronoUnit.SECONDS)

        val accessTokenExpire = accessTokenTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        val refreshTokenExpire = refreshTokenTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        val accessToken = createToken(
            name = email,
            tokenExpireTime = accessTokenExpire,
            role = role
        )
        val refreshToken = createToken(
            name = email,
            tokenExpireTime = refreshTokenExpire,
            role = role
        )
        return LoginToken(accessToken = accessToken, refreshToken = refreshToken, accessTokenExpiredAt = Date(accessTokenExpire), refreshTokenExpiredAt = Date(refreshTokenExpire) )
    }

    fun getAuthentication(token: String) : Authentication {
        val trainer = trainerRepository.findByEmailAndRole(getTrainer(token), Role.TRAINER) ?: throw KepaException(
            ExceptionCode.NOT_EXSISTS_INFO
        )
        val loginUserDetail = LoginUserDetail(trainer.email, trainer.role.name);
        println(loginUserDetail.authorities)
        return UsernamePasswordAuthenticationToken(loginUserDetail,"" , loginUserDetail.authorities)
    }

    fun getTrainer(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }


    fun validateToken(token: String): Boolean {
         try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            return true
        }catch (e : SecurityException) {
            e.printStackTrace()
        }catch (e : ExpiredJwtException) {
             e.printStackTrace()
        }catch (e: UnsupportedJwtException) {
             e.printStackTrace()
        }catch (e: IllegalArgumentException) {
             e.printStackTrace()
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest?): String? {
        return request?.getHeader("Authorization")}

    //토큰 생성
    private fun createToken(name: String, tokenExpireTime: Long, role: String): String {
        return Jwts.builder()
            .setSubject(name)
            .claim("auth",role)
            .setExpiration(Date(tokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}