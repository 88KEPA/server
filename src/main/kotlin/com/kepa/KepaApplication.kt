package com.kepa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing(auditorAwareRef = "baseEntityConfig")
@SpringBootApplication
class KepaApplication

fun main(args: Array<String>) {
    runApplication<KepaApplication>(*args)
}
