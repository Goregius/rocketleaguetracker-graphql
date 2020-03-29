package com.github.goregius.rankinflation.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("rl-tracker")
data class RlTrackerProperties(val endpoint: EndpointProperties, val page: PageProperties) {
    data class EndpointProperties(val baseUrl: String = "https://rocketleague.tracker.network")

    data class PageProperties(val rankedRatingLeaderboards: RankedRatingLeaderboardsProperties) {
        data class RankedRatingLeaderboardsProperties(val ranksPerPage: Int = 100)
    }
}