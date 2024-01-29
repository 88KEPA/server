package com.kepa.externalapi

import com.kepa.application.partner.dto.request.PartnerMessageContent
import com.kepa.application.user.trainer.dto.request.MessageContent
import com.kepa.application.user.trainer.dto.response.MessageSendStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class MessageApiService(
    @Value("\${aligo.key}")
    private val aligoKey: String,
    @Value("\${aligo.send-url}")
    private val aligoSendUrl: String,
    @Value("\${aligo.send-phone-number}")
    private val sendPhoneNumber: String,
    @Value("\${aligo.send-user-id}")
    private val sendUserId: String,
) {

    @TransactionalEventListener
    fun joinSendMessage(messageContent: MessageContent) {
        val restTemplate = RestTemplate()
        val header = getHeaders()
        val requestValue : MultiValueMap<String, String> = getCommonRequestValue(messageContent.receiverPhoneNumber)
        requestValue.add("msg", "[KEPA]본인확인 인증번호 [${messageContent.certNumber}]를 화면에 입력해주세요.")
        request(requestValue, header, restTemplate)
    }

    private fun request(requestValue: MultiValueMap<String, String>, header: HttpHeaders, restTemplate: RestTemplate) {
        val httpEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(requestValue, header)
        restTemplate.exchange(
            aligoSendUrl, HttpMethod.POST, httpEntity, MessageSendStatus::class.java
        )
    }

    @TransactionalEventListener
    fun createPartnerMessage(partnerMessageContent: PartnerMessageContent) {
        val restTemplate = RestTemplate()
        val header = getHeaders()
        val requestValue : MultiValueMap<String, String> = getCommonRequestValue(partnerMessageContent.receiverPhoneNumber)
        requestValue.add("msg", "${partnerMessageContent.name}님 KEPA 서비스 이용에 관심을 가져주셔서 감사합니다. 운동을 통한 건강을 위해 고객님께 하루빨리 연락드리겠습니다" )
        request(requestValue, header, restTemplate)
    }

    private fun getHeaders(): HttpHeaders {
        val header = HttpHeaders()
        header.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        return header
    }

    private fun getCommonRequestValue(receiverPhoneNumber: String): MultiValueMap<String, String> {
        val requestValue : MultiValueMap<String, String> = LinkedMultiValueMap()
        requestValue.add("key", aligoKey)
        requestValue.add("user_id", sendUserId)
        requestValue.add("sender", sendPhoneNumber)
        requestValue.add("receiver", receiverPhoneNumber)
        return requestValue
    }
}