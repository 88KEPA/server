package com.kepa.application.user.dto.response

import Gender
import Role
import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.domain.user.account.Account
import java.time.LocalDate

data class DetailInfo(
    val name: String,
    val gender: Gender,
    val email: String,
    @JsonFormat(pattern = "yyyy.MM.dd")
    val birth: LocalDate,
    val address: String,
    val addressMeta: String,
    val addressDetail: String,
    val phone: String,
    val role: Role
) {
    companion object {
        fun create(account: Account) : DetailInfo{
            return DetailInfo(
                name = account.name,
                gender =  account.gender,
                email = account.email,
                birth = account.birth,
                address = account.address,
                addressMeta = account.addressMeta,
                addressDetail = account.addressDetail,
                phone = account.phone,
                role =  account.role
            )
        }
    }
}

data class PageResponse(
    val contents: List<Any>,
    val totalCount: Long,
    val page: Int,
    var limit: Int,
    var totalPageCount: Long,
) {
    companion object {
        fun toResponse(contents: List<Any>,
                   totalCount: Long,
                   page: Int,
                   limit: Int,
                   totalPageCount: Long) : PageResponse {
            return PageResponse(
                contents = contents,
                totalCount = totalCount,
                page = page,
                limit = limit,
                totalPageCount = totalPageCount
            )
        }
    }
}

