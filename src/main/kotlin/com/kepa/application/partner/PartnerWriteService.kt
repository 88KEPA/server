package com.kepa.application.partner

import com.kepa.application.partner.dto.request.PartnerCreate
import com.kepa.application.partner.dto.request.PartnerMessageContent
import com.kepa.domain.partner.PartnerRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PartnerWriteService(
    private val partnerRepository: PartnerRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
)  {

    fun applyPartner(partnerCreate: PartnerCreate) {
        partnerRepository.save(partnerCreate.createPartner())
        applicationEventPublisher.publishEvent(PartnerMessageContent(partnerCreate.phone,partnerCreate.name))
    }
}