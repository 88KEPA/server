package com.kepa.domain.user.account

import Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository : JpaRepository<Account,Long> {
    fun existsByEmail(email: String) : Boolean

    fun findByEmailAndRole(@Param("email")email: String?, @Param("role")role: Role) : Account?

    fun findByPhone(phoneNumber: String) : Account?

    fun existsByPhone(phoneNumber: String): Boolean

    fun findByEmail(email: String) : Account?

    fun findAllByRole(role: Role) : List<Account>

    @Query(value = "SELECT a FROM Account a WHERE (a.email = :keyword OR a.name = :keyword OR a.phone = :keyword) AND a.role = :role")
    fun findAllByEmailOrNameOrPhone(@Param("keyword")keyword: String, @Param("role") role: Role): List<Account>

    @Modifying
    @Query(value = """
        UPDATE Account SET name = null, phone = null, birth = null, password = null, address = null, addressDetail = null,
        addressMeta = null,email = null, withdrawAt = now(), role = 'WITHDRAW' where id = :id
        """)
    fun withdrawAccount(@Param("id") id: Long)
}