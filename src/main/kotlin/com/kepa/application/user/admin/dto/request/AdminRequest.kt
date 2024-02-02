package com.kepa.application.user.admin.dto.request

abstract class PageRequest(
    val limit: Int = 10,
    val page: Int = 0
)

data class FilterRequest(
    val keyword: String?,
) : PageRequest()
