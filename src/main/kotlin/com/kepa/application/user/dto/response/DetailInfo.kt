package com.kepa.application.user.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.domain.user.account.Account
import com.kepa.domain.user.enums.Gender
import com.kepa.domain.user.enums.Role
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
        fun create(account: Account): DetailInfo {
            return DetailInfo(
                name = account.name,
                gender = account.gender,
                email = account.email,
                birth = account.birth,
                address = account.address,
                addressMeta = account.addressMeta,
                addressDetail = account.addressDetail,
                phone = account.phone,
                role = account.role
            )
        }
    }
}

data class PageResponse<T>(
    val contents: List<T>,
    val totalCount: Long,
    val page: Int,
    var limit: Int,
    var totalPageCount: Long,
) {
    companion object {
        fun <T> toResponse(
            contents: List<T>,
            totalCount: Long,
            page: Int,
            limit: Int,
            totalPageCount: Long): PageResponse<T> {
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

