package com.example.opentelemetry_zero_code_instrumentation_practice

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomAuth {
    @Bean
    fun otelCustomAuthCustomizer(): AutoConfigurationCustomizerProvider {
        return AutoConfigurationCustomizerProvider { p ->
            p.addSpanExporterCustomizer { exporter, _ ->
                if (exporter is OtlpHttpSpanExporter) {
                    exporter.toBuilder()
                        .setHeaders { headers() }
                        .build()
                } else {
                    exporter
                }
            }
        }
    }

    private fun headers(): Map<String, String> {
        return mapOf("Authorization" to "Bearer ${refreshToken()}")
    }

    private fun refreshToken(): String {
        // e.g. read the token from a kubernetes secret
        return "token"
    }
}
