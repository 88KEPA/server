package com.kepa.application.notification

import com.kepa.application.notification.dto.request.PageRequest
import com.kepa.application.notification.dto.response.NotificationDetail
import com.kepa.application.notification.dto.response.PageResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["[Common + Notification] 공지사항"])
@RestController
@RequestMapping("/api/notification")
class NotificationController(
    private val notificationReadService: NotificationReadService,
) {

    @ApiOperation("공지사항 상세보기")
    @GetMapping("/{notificationId}")
    fun getDetail(@PathVariable(value = "notificationId") notificationId: Long): NotificationDetail {
        return notificationReadService.getDetail(notificationId)
    }
    @ApiOperation("공지사항 목록")
    @GetMapping
    fun get(pageRequest: PageRequest): PageResponse {
        return notificationReadService.get(limit = pageRequest.limit, page = pageRequest.page)
    }
}