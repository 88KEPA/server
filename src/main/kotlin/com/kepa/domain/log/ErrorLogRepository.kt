package com.kepa.domain.log

import org.springframework.data.jpa.repository.JpaRepository

interface ErrorLogRepository : JpaRepository<ErrorLog,Long> {
}