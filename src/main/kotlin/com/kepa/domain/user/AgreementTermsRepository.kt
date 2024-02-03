package com.kepa.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface AgreementTermsRepository : JpaRepository<AgreementTerms, Long> {
}