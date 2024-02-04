package com.kepa.application.user.admin.dto.request

abstract class PageRequest(
    var limit: Int = 10,
    var page: Int = 0
)

data class FilterRequest(
    val keyword: String?,
) : PageRequest()
