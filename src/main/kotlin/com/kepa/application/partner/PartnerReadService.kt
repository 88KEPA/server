package com.kepa.application.partner

import com.kepa.application.partner.dto.response.PartnerInfoDetail
import com.kepa.domain.partner.PartnerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PartnerReadService(
    private val partnerRepository: PartnerRepository,
) {

    fun findAll(): List<PartnerInfoDetail> {
        return partnerRepository.findAll().map {
            PartnerInfoDetail(
                name = it.name,
                email = it.email,
                phone = it.phone,
                content = it.content,
                organization = it.organization,
                position = it.position
            )
        }
    }
}