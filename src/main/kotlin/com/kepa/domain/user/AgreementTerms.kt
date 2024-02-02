package com.kepa.domain.user

import com.kepa.common.BaseEntity
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.terms.Terms
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class AgreementTerms(
    val isAgree: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    val account: Account,
    @ManyToOne(fetch = FetchType.LAZY)
    val terms: Terms,
) : BaseEntity() {
}