package com.kepa.config

import com.kepa.domain.user.account.Account
import com.kepa.domain.user.account.AccountRepository
import com.kepa.security.LoginUserDetail
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

/**
 * packageName    : com.kepa.config
 * fileName       : BaseEntityConfig
 * author         : hoewoonjeong
 * date           : 3/13/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/13/24        hoewoonjeong               최초 생성
 */
@Component
class BaseEntityConfig(
    val accountRepository: AccountRepository,
) : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map { it.authentication }
            .filter { it.isAuthenticated && !it.name.equals("anonymousUser") }
            .map { it.principal as LoginUserDetail }
            .map { accountRepository.findByEmail(it.email) }
            .map { it?.id }
    }
}