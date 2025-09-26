package com.example.opentelemetry_zero_code_instrumentation_practice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {
    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}
