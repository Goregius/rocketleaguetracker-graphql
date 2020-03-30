package com.github.goregius.rankinflation

import com.github.goregius.rankinflation.configuration.properties.RlTrackerProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableConfigurationProperties(RlTrackerProperties::class)
@EnableJpaRepositories
class RankInflationApplication

fun main(args: Array<String>) {
    runApplication<RankInflationApplication>(*args)
}
