package com.kepa.application.notification.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.kepa.domain.notification.enums.FileType
import com.kepa.domain.notification.enums.NotificationType
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime

@ApiModel("공지사항 상세보기")
data class NotificationDetail(
    @ApiModelProperty("내용")
    val content: String,
    @ApiModelProperty("제목")
    val title: String,
    @ApiModelProperty("공지사항 타입")
    val notificationType: NotificationType,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime,
    @ApiModelProperty("이미지, 파일정보")
    var notificationFileDetail: List<NotificationFileDetail> = listOf()
) {
    fun createNotificationFile(notificationFileList: List<NotificationFileDetail>) {
        this.notificationFileDetail = notificationFileList

    }
}

@ApiModel("파일 정보")
data class NotificationFileDetail(
    @ApiModelProperty("파일 설명")
    val alt: String,
    @ApiModelProperty("파일 url")
    val src: String,
    @ApiModelProperty("파일 타입")
    val fileType: FileType,
    @ApiModelProperty("정렬순서")
    val orderNum: Int,
)

data class Notifications(
    @ApiModelProperty("내용")
    val content: String,
    @ApiModelProperty("제목")
    val title: String,
    @ApiModelProperty("공지사항 타입")
    val notificationType: NotificationType,
    @ApiModelProperty("생성 시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @ApiModelProperty("수정 시간")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime,
)

data class PageResponse(
    val limit : Int,
    val page: Int,
    val totalCount: Long,
    val notifications: List<Notifications>,
)

