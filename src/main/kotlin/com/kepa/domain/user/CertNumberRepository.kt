package com.kepa.domain.user

import CertType
import org.springframework.data.jpa.repository.JpaRepository

interface CertNumberRepository: JpaRepository<CertNumber, Long> {

    fun existsByReceiverEmailAndCertType(email: String, certType: CertType): Boolean
    fun deleteByReceiverEmailAndCertType(email: String,certType: CertType)

    fun existsByReceiverPhoneNumberAndCertType(phoneNumber: String, certType: CertType): Boolean
    fun deleteByReceiverPhoneNumberAndCertType(phoneNumber: String,certType: CertType)

    fun findByReceiverEmailAndReceiverPhoneNumberAndCertType(email: String, phoneNumber: String, certType: CertType) : CertNumber?
    fun findByReceiverEmailAndCertType(email: String, certType: CertType) : CertNumber?

    fun findByReceiverPhoneNumberAndCertType(phoneNumber: String, certType: CertType) : CertNumber?

    fun findByNumberAndReceiverEmailAndCertType(number: Int,email: String ,certType: CertType) : CertNumber?

    fun findByNumberAndReceiverPhoneNumberAndCertType(number: Int,phoneNumber: String ,certType: CertType) : CertNumber?
}