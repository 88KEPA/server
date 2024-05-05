package com.kepa.domain.user.account

import com.kepa.domain.user.enums.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository : JpaRepository<Account,Long>, AccountQueryDslRepository {
    fun existsByEmail(email: String) : Boolean

    fun findByEmailAndRole(@Param("email")email: String?, @Param("role")role: Role) : Account?

    fun findByPhone(phoneNumber: String) : Account?

    fun existsByPhone(phoneNumber: String): Boolean

    fun findByEmail(email: String) : Account?

    @Modifying
    @Query(value = """
        UPDATE Account SET name = null, phone = null, birth = null, password = null, address = null, addressDetail = null,
        addressMeta = null,email = null, withdrawAt = now(), role = 'WITHDRAW' where id = :id
        """)
    fun withdrawAccount(@Param("id") id: Long)
}