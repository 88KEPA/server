package com.kepa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KepaApplication

fun main(args: Array<String>) {
    runApplication<KepaApplication>(*args)
}
