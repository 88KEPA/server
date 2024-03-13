package com.kepa.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class LoginUserDetail(
    val email: String,
    val role: String?,
) : UserDetails{

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        if(role == null) {
            return mutableListOf()
        }
        return mutableListOf(SimpleGrantedAuthority("ROLE_${role}"))
    }

    override fun getPassword(): String = ""

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}