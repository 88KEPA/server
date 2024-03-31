package com.kepa.domain.notification

import com.kepa.common.BaseWithAccountEntity
import com.kepa.domain.notification.enums.FileType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class NotificationFile(
    @Enumerated(EnumType.STRING)
    var fileType: FileType,
    var src: String,
    var alt: String,
    var orderNum: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    var notification: Notification,
) : BaseWithAccountEntity() {

}