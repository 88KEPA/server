package com.kepa.domain.user

import Role

interface User {
    val role: Role
    val email: String
}