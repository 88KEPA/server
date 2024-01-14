package com.kepa.externalapi

import com.kepa.application.trainer.dto.request.MessageContent
import com.kepa.application.trainer.dto.response.MessageSendStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Transactional
@Service
class MessageApiService(
    @Value("\${aligo.key}")
    private val aligoKey: String,
    @Value("\${aligo.send-url}")
    private val aligoSendUrl: String,
    @Value("\${aligo.send-phone-number}")
    private val sendPhoneNumber: String,
) {

    @TransactionalEventListener
    fun joinSendMessage(messageContent: MessageContent) {
        val restTemplate = RestTemplate()
        val requestValue : MultiValueMap<String, String> = LinkedMultiValueMap()
        val header = HttpHeaders()
        header.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        requestValue.add("key", aligoKey)
        requestValue.add("user_id", messageContent.userId)
        requestValue.add("sender", sendPhoneNumber)
        requestValue.add("receiver", messageContent.receiverPhoneNumber)
        requestValue.add("msg", "[KEPA]본인확인 인증번호 [${messageContent.randomNumber}]를 화면에 입력해주세요.")
        val httpEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(requestValue,header)
        restTemplate.exchange(
            aligoSendUrl, HttpMethod.POST, httpEntity, MessageSendStatus::class.java
        )
    }
}