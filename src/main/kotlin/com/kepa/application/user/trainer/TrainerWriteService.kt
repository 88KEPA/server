package com.kepa.application.user.trainer

import com.kepa.application.user.trainer.dto.request.AccountJoin
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.ExceptionCode.*
import com.kepa.common.exception.KepaException
import com.kepa.domain.user.AgreementTerms
import com.kepa.domain.user.AgreementTermsRepository
import com.kepa.domain.user.CertNumberRepository
import com.kepa.domain.user.account.AccountRepository
import com.kepa.domain.user.terms.TermsRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Transactional
@Service
class TrainerWriteService(
    private val accountRepository: AccountRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val certNumberRepository: CertNumberRepository,
    private val termsRepository: TermsRepository,
    private val agreementTermsRepository: AgreementTermsRepository,
) {
    fun join(trainerJoin: AccountJoin) {
        require(!trainerJoin.phone.contains("-")) {
            throw KepaException(BAD_REQUEST_PHONE_FORMAT)
        }
        require(
            !accountRepository.existsByEmail(
                trainerJoin.email
            )
        ) {
            throw KepaException(ALREADY_INFORMATION)
        }
        //추후에 약관 늘어나면 코드 개선 (급해서 작성한 코드입니다) 쿼리 개선
        val terms = termsRepository.findByIdOrNull(trainerJoin.agrees[0]) ?: throw KepaException(NOT_EXSITS_TERMS)
        val savedAccount = accountRepository.save(trainerJoin.create(bCryptPasswordEncoder.encode(trainerJoin.password)))
        agreementTermsRepository.save(AgreementTerms(account = savedAccount, terms =  terms))
    }

    fun changePassword(certId: Int?, email: String?, password: String?) {
        if (certId == null || email == null || password == null) {
            throw KepaException(BAD_REQUEST)
        }
        val certNumber = certNumberRepository.findByNumberAndReceiverEmailAndCertType(certType = CertType.FIND_RESULT, number = certId, email = email)
            ?: throw KepaException(NOT_EXSISTS_INFO)

        require(certNumber.createdAt.plusMinutes(TrainerCertWriteService.CHECKT_EXPIRE_TIME).isAfter(LocalDateTime.now())) {
            certNumberRepository.deleteById(certNumber.id)
            throw KepaException(EXPIRE_CERT_NUMBER)
        }
        certNumberRepository.deleteById(certNumber.id)
        val findAccount = accountRepository.findByEmail(email) ?: throw KepaException(NOT_EXSISTS_INFO)
        findAccount.password = bCryptPasswordEncoder.encode(password)
    }
}