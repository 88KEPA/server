package com.kepa.domain.notification

import com.kepa.common.BaseWithAccountEntity
import com.kepa.domain.notification.enums.NotificationType
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToMany

@Entity
class Notification(
    @Enumerated(EnumType.STRING)
    var notificationType: NotificationType,
    @OneToMany(mappedBy = "notification", cascade = [CascadeType.ALL])
    var notificationFile: MutableList<NotificationFile> = mutableListOf(),
    var content: String,
    var title: String,
) : BaseWithAccountEntity() {

    fun updateFile(notificationFile: MutableList<NotificationFile>) {
        this.notificationFile = notificationFile;
    }

    fun update(notificationType: NotificationType, content: String, title: String) {
        this.notificationType = notificationType
        this.content = content
        this.title = title
    }
}