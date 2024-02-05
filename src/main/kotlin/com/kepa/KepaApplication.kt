package com.kepa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*
import javax.annotation.PostConstruct

@EnableJpaAuditing
@SpringBootApplication
class KepaApplication

fun main(args: Array<String>) {
    runApplication<KepaApplication>(*args)
}
