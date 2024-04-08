package com.kepa.application.notification

import com.kepa.application.notification.dto.response.NotificationDetail
import com.kepa.application.notification.dto.response.NotificationFileDetail
import com.kepa.application.notification.dto.response.Notifications
import com.kepa.application.notification.dto.response.PageResponse
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import com.kepa.domain.notification.Notification
import com.kepa.domain.notification.NotificationFileRepository
import com.kepa.domain.notification.NotificationRepository
import com.kepa.file.s3.S3FileManagement
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class NotificationReadService(
    private val notificationRepository: NotificationRepository,
    private val notificationFileRepository: NotificationFileRepository,
    private val s3FileManagement: S3FileManagement,
) {

    fun getDetail(notificationId: Long): NotificationDetail {
        val notification = notificationRepository.findByIdOrNull(notificationId)
            ?: throw KepaException(ExceptionCode.NOT_EXSISTS_INFO)
        val notificationFiles = notificationFileRepository.findFileByNotificationId(notification.id)
        val notificationDetail = NotificationDetail(content = notification.content, title = notification.title,
            notificationType = notification.notificationType, createdAt = notification.createdAt, updatedAt = notification.updatedAt)
        val notificationFileDetails = notificationFiles?.map {
            NotificationFileDetail(it.alt, s3FileManagement.getFile(it.src), it.fileType, it.orderNum)
        } ?: mutableListOf()
        notificationDetail.createNotificationFile(notificationFileDetails)
        return notificationDetail
    }

    fun get(limit: Int, page: Int): PageResponse {
        val pageRequest = PageRequest.of(page, limit)
        val notifications = notificationRepository.findAll(pageRequest).map {
            Notifications(content = it.content, title = it.title, notificationType = it.notificationType,
                createdAt = it.createdAt, updatedAt = it.updatedAt, id = it.id)
        }
        return PageResponse(limit = limit, page =page, totalCount = notifications.totalElements,notifications = notifications.content)
    }
}