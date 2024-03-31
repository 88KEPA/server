package com.kepa.application.notification

import com.kepa.application.notification.dto.request.NotificationCreate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Api(tags = ["[Admin + Notification] 공지사항"])
@Secured("ROLE_ADMIN","ROLE_SUPER_ADMIN")
@RestController
@RequestMapping("/api/admin/notification")
class NotificationAdminController(
    private val notificationAdminWriteService: NotificationAdminWriteService,
) {

    @ApiOperation(value = "공지사항 등록")
    @PostMapping
    fun create(@RequestPart notificationCreate: NotificationCreate,
               @RequestPart fileSrc: List<MultipartFile>?,
               @RequestPart imageSrc: List<MultipartFile>?,) {
        notificationAdminWriteService.create(
            title =  notificationCreate.title,
            content = notificationCreate.content,
            imageAlt = notificationCreate.imageAlt,
            fileAlt = notificationCreate.fileAlt,
            imageSrc = imageSrc,
            fileSrc = fileSrc,
            notificationType =  notificationCreate.notificationType
        )
    }
}