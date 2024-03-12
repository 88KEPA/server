package com.kepa.security

import Role
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.annotation.LoginUser
import com.kepa.domain.user.account.AccountRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class LoginInterception(
    private val accountRepository: AccountRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val role = authentication.authorities.first().toString().split("_")
        var authenRole =
            if (role.size == 2) {
                role[1]
            } else {
                "${role[1]}_${role[2]}"
            }
        if(authenRole == "ANONYMOUS") {
            throw KepaException(ExceptionCode.NO_AUTHENTICATION)
        }
        val account = accountRepository.findByEmailAndRole(
            authentication.name, Role.valueOf(authenRole)
        ) ?: throw KepaException(ExceptionCode.TOKEN_EXPIRE)
        return LoginUserInfo(account.id, account.role)
    }
}