package com.ziola.postsreader.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PlaceholderClientConfig {
    @Bean
    fun restClient(
        @Value("\${placeholder.base.url}") baseUrl: String,
    ): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .build()
    }
}
