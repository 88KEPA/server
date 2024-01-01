package com.kepa.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface CertNumberRepository: JpaRepository<CertNumber, Long> {

    fun existsByReceiverEmail(email: String): Boolean
    fun deleteByReceiverEmail(email: String)
}