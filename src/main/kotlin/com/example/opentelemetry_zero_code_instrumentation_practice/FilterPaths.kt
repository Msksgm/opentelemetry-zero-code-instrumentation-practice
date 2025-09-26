package com.example.opentelemetry_zero_code_instrumentation_practice

import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.contrib.sampler.RuleBasedRoutingSampler
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider
import io.opentelemetry.semconv.UrlAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterPaths {

    // actuator パスをフィルタリングするカスタマイズ
    @Bean
    fun otelFilterPathCustomizer(): AutoConfigurationCustomizerProvider {
        return AutoConfigurationCustomizerProvider { p ->
            p.addSamplerCustomizer { fallback, config ->
                RuleBasedRoutingSampler.builder(SpanKind.SERVER, fallback)
                    .drop(UrlAttributes.URL_PATH, "^/actuator")
                    .build()
            }
        }
    }
}
