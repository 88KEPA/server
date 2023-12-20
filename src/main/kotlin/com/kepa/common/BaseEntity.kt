package com.kepa.common

import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("기본키")
    val id: Long = 0
    @CreatedDate
    @Column(updatable = false, nullable = false)
    @Comment("생성 시간")
    val createdAt: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate
    @Comment("수정 시간")
    var updatedAt: LocalDateTime? = null
}