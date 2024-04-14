package com.kepa.application.notification

import com.kepa.application.notification.dto.request.NotificationCreate
import com.kepa.application.notification.dto.request.NotificationUpdate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["[Admin + Notification] 공지사항"])
@Secured("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
@RestController
@RequestMapping("/api/admin/notification")
class NotificationAdminController(
    private val notificationAdminWriteService: NotificationAdminWriteService,
) {

    @ApiOperation(value = "등록")
    @PostMapping
    fun create(
        @RequestPart notificationCreate: NotificationCreate,
        @RequestPart fileSrc: List<MultipartFile>?,
        @RequestPart imageSrc: List<MultipartFile>?,
    ) {
        notificationAdminWriteService.create(
            title = notificationCreate.title,
            content = notificationCreate.content,
            imageAlt = notificationCreate.imageAlt,
            fileAlt = notificationCreate.fileAlt,
            imageSrc = imageSrc,
            fileSrc = fileSrc,
            notificationType = notificationCreate.notificationType
        )
    }

    @ApiOperation(value = "삭제")
    @DeleteMapping("/{notificationId}")
    fun delete(@PathVariable notificationId: Long) {
        notificationAdminWriteService.delete(notificationId)
    }

    @ApiOperation(value = "수정")
    @PutMapping("/{notificationId}")
    fun update(
        @PathVariable notificationId: Long,
        @RequestPart notificationUpdate: NotificationUpdate,
        @RequestPart fileSrc: List<MultipartFile>?,
        @RequestPart imageSrc: List<MultipartFile>?,
    ) {
        notificationAdminWriteService.update(
            notificationId = notificationId,
            title = notificationUpdate.title,
            content = notificationUpdate.content,
            imageAlt = notificationUpdate.imageAlt,
            fileAlt = notificationUpdate.fileAlt,
            imageSrc = imageSrc,
            fileSrc = fileSrc,
            notificationType = notificationUpdate.notificationType)
    }
}