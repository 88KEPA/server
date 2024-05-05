package com.kepa.application.user.admin.dto.request

import com.kepa.application.user.admin.dto.request.enums.Sort

abstract class PageRequest(
    var limit: Int = 10,
    var page: Int = 0
)
data class FilterRequest(
    val keyword: String?,
    val sort: Sort = Sort.ASC,
    val isResource: Boolean?
) : PageRequest() {
    fun getIsResource(): Boolean? {
        return isResource
    }
}
