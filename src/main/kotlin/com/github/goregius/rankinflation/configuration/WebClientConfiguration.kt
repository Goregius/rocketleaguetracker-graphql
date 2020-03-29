package com.github.goregius.rankinflation.configuration

import com.github.goregius.rankinflation.configuration.properties.RlTrackerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration(
    val rlTrackerProperties: RlTrackerProperties
) {
    @Bean
    fun rlTrackerWebClient(): WebClient = WebClient.create(rlTrackerProperties.endpoint.baseUrl)
}