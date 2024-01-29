package com.kepa.application.partner

import com.kepa.application.partner.dto.request.PartnerCreate
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Api(tags = ["[Partner] 제휴관련 API"])
@RequestMapping("/api/partner")
class PartnerController(
    private val partnerWriteService: PartnerWriteService,
) {

    //등록
    @PostMapping
    fun create(@RequestBody partnerCreate: PartnerCreate) {
        partnerWriteService.applyPartner(partnerCreate)

    }
}