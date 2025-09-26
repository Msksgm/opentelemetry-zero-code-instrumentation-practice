package com.example.opentelemetry_zero_code_instrumentation_practice

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.api.trace.Tracer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(openTelemetry: OpenTelemetry) {
    private val tracer: Tracer = openTelemetry.getTracer("application")

    @GetMapping("/ping")
    fun ping(): String {
        this.pingSpan()
        return "pong"
    }

    fun pingSpan() {
        val span = tracer.spanBuilder("pingSpan").startSpan()
        span.end()
        return
    }
}
