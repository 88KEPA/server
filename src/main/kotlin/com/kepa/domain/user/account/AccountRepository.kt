package com.kepa.domain.user.account

import Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface AccountRepository : JpaRepository<Account,Long> {
    fun existsByEmail(email: String) : Boolean

    fun findByEmailAndRole(@Param("email")email: String?, @Param("role")role: Role) : Account?

    fun findByPhone(phoneNumber: String) : Account?

    fun existsByPhone(phoneNumber: String): Boolean

    fun findByEmail(email: String) : Account?

    fun findAllByRole(role: Role) : List<Account>
}