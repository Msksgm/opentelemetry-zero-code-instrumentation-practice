package com.example.opentelemetry_zero_code_instrumentation_practice

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.instrumentation.annotations.SpanAttribute
import io.opentelemetry.instrumentation.annotations.WithSpan
import org.springframework.stereotype.Component

@Component
class TracedClass {

    @WithSpan
    fun tracedMethod() {}

    @WithSpan(value = "TracedClass span name")
    fun tracedMethodWithSpanName() {
        val currentSpan = Span.current()
        currentSpan.addEvent("ADD EVENT TO tracedMethodWithName SPAN");
        currentSpan.setAttribute("isTestAttribute", true);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    fun tracedMethodWithoutAnnotation() {}

    @WithSpan
    fun tracedMethodWithAttribute(@SpanAttribute("attributeName") parameter: String) {}
}
