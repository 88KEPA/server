package com.kepa.domain.user.account

import com.kepa.application.user.admin.dto.request.enums.Sort
import com.kepa.domain.user.enums.Role

interface AccountQueryDslRepository {

    fun findAllByEmailOrNameOrPhone(keyword: String?, role: Role, sort: Sort, isResource: Boolean?) : List<Account>
}