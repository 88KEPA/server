package com.kepa.security

import Role
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.annotation.LoginUser
import com.kepa.domain.user.trainer.TrainerRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class LoginInterception(
    private val trainerRepository: TrainerRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val trainer = trainerRepository.findByEmailAndRole(
            authentication.name, Role.valueOf(authentication.authorities.first().toString())
        )
            ?: throw KepaException(ExceptionCode.TOKEN_EXPIRE)
        return LoginUserInfo(trainer.id, trainer.role)
    }
}