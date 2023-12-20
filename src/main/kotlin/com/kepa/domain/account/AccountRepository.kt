package com.kepa.domain.account

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account,Long> {
    fun existsByLoginIdOrEmail(loginId: String, email: String) : Boolean
}