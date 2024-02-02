package com.kepa.domain.partner

import org.springframework.data.jpa.repository.JpaRepository

interface PartnerRepository : JpaRepository<Partner, Long> {
    fun findAllByOrganization(organization: String) : List<Partner>
}