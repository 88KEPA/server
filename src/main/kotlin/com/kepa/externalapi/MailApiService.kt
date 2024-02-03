package com.kepa.externalapi

import com.kepa.application.user.trainer.dto.request.MailContent
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MailApiService(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}")
    private val senderEmail: String,

) {

    @TransactionalEventListener
    fun sendMail(mailContent: MailContent) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setTo(mailContent.email)
        simpleMailMessage.from = senderEmail
        simpleMailMessage.subject = "[KEPA] 이메일 인증번호"
        simpleMailMessage.text = "인증번호 [${mailContent.certNumber}]"
        javaMailSender.send(simpleMailMessage)
        println("메일전송")
    }
}
