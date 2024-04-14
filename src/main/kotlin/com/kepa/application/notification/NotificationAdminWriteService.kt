package com.kepa.application.notification

import com.kepa.common.exception.ExceptionCode.NOT_EXSITS_NOTIFICATION
import com.kepa.common.exception.KepaException
import com.kepa.domain.notification.Notification
import com.kepa.domain.notification.NotificationFile
import com.kepa.domain.notification.NotificationFileRepository
import com.kepa.domain.notification.NotificationRepository
import com.kepa.domain.notification.enums.FileType
import com.kepa.domain.notification.enums.NotificationType
import com.kepa.file.s3.S3FileManagement
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Transactional
@Service
class NotificationAdminWriteService(
    private val notificationRepository: NotificationRepository,
    private val notificationFileRepository: NotificationFileRepository,
    private val s3FileManagement: S3FileManagement,
) {

    fun create(
        title: String,
        content: String,
        imageAlt: List<String?>?,
        fileAlt: List<String?>?,
        notificationType: NotificationType,
        imageSrc: List<MultipartFile>?,
        fileSrc: List<MultipartFile>?
    ) {
        val images = imageSrc?.let{createFile(imageSrc, FileType.IMAGE)}
        val files = fileSrc?.let{createFile(fileSrc, FileType.FILE)}
        val notification = Notification(
            notificationType = notificationType,
            content = content,
            title = title,
        )
        val savedNotification = notificationRepository.save(notification)
        val notificationFileList: MutableList<NotificationFile> = mutableListOf()
        images?.let {
            for (index in 0 until images.size) {
                notificationFileList.add(NotificationFile(fileType = FileType.IMAGE, src = images.get(index), alt = imageAlt?.get(index) ?: "", orderNum = index, savedNotification))
            }
        }
        files?.let {
            for (index in 0 until files.size) {
                notificationFileList.add(NotificationFile(fileType = FileType.FILE, src = files.get(index), alt = fileAlt?.get(index) ?: "", orderNum = index, savedNotification))
            }
        }
        notificationFileRepository.saveAll(notificationFileList)
    }

    fun delete(notificationId: Long) {
        val notification = notificationRepository.findByIdOrNull(notificationId)
            ?: throw KepaException(NOT_EXSITS_NOTIFICATION)
        notificationRepository.delete(notification)
    }

    fun update(notificationId: Long,
               title: String,
               content: String,
               imageAlt: List<String?>?,
               fileAlt: List<String?>?,
               notificationType: NotificationType,
               imageSrc: List<MultipartFile>?,
               fileSrc: List<MultipartFile>?) {
        val notification = notificationRepository.findByIdOrNull(notificationId)
            ?: throw KepaException(NOT_EXSITS_NOTIFICATION)
        val images = imageSrc?.let{createFile(imageSrc, FileType.IMAGE)}
        val files = fileSrc?.let{createFile(fileSrc, FileType.FILE)}
        val notificationFileList: MutableList<NotificationFile> = mutableListOf()
        images?.let {
            for (index in 0 until images.size) {
                notificationFileList.add(NotificationFile(fileType = FileType.IMAGE, src = images.get(index), alt = imageAlt?.get(index) ?: "", orderNum = index, notification = notification))
            }
        }
        files?.let {
            for (index in 0 until files.size) {
                notificationFileList.add(NotificationFile(fileType = FileType.FILE, src = files.get(index), alt = fileAlt?.get(index) ?: "", orderNum = index, notification = notification))
            }
        }
        notification.updateFile(notificationFileList)
        notification.update(notificationType = notificationType, content = content, title = title)
    }

    private fun createFile(requestSrcList: List<MultipartFile>, fileType: FileType): List<String> {
        val srcList: MutableList<String> = mutableListOf()
        if (fileType == FileType.FILE) {
            srcList.addAll(requestSrcList.map { s3FileManagement.uploadFile(it) })
            return srcList
        }
        srcList.addAll(requestSrcList.map { s3FileManagement.uploadImage(it) })
        return srcList
    }

}