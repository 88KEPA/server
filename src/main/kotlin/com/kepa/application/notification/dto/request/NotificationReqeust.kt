package com.kepa.application.notification.dto.request

import com.kepa.domain.notification.enums.NotificationType
import io.swagger.annotations.ApiModelProperty

data class NotificationCreate(
    @ApiModelProperty("제목")
    val title: String,
    @ApiModelProperty("내용")
    val content: String,
    @ApiModelProperty("공지 타입 - NOTIFICATION(공지), COMMUNITY(커뮤니티)")
    val notificationType: NotificationType,
    val imageAlt: List<String?>?,
    val fileAlt: List<String?>?,
)

data class NotificationUpdate(
    @ApiModelProperty("제목")
    val title: String,
    @ApiModelProperty("내용")
    val content: String,
    @ApiModelProperty("공지 타입 - NOTIFICATION(공지), COMMUNITY(커뮤니티)")
    val notificationType: NotificationType,
    val imageAlt: List<String?>?,
    val fileAlt: List<String?>?,
)

data class PageRequest(
    var limit: Int = 10,
    var page: Int = 0
)