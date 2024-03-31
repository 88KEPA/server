package com.kepa.domain.notification

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationFileRepository : JpaRepository<NotificationFile, Long> {

    @Query("SELECT nf FROM NotificationFile nf JOIN FETCH nf.notification n WHERE n.id = :notificationId")
    fun findFileByNotificationId(@Param("notificationId") notificationId: Long): List<NotificationFile>?
}