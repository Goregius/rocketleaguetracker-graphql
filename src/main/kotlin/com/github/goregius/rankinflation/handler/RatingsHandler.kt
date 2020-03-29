package com.github.goregius.rankinflation.handler

import com.github.goregius.rankinflation.repository.LeaderboardRatingRepository
import com.github.goregius.rankinflation.service.ratingleaderboards.LeaderboardRatingsUpdateService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.json
import java.time.Instant

@Service
class RatingsHandler(
    val leaderboardRatingRepository: LeaderboardRatingRepository,
    val leaderboardRatingsUpdateService: LeaderboardRatingsUpdateService
) {
    val scope = CoroutineScope(Job())

    suspend fun findAll(serverRequest: ServerRequest): ServerResponse =
        ServerResponse.ok()
            .json().bodyAndAwait(leaderboardRatingRepository.findAll().asFlow())

    suspend fun fetch(serverRequest: ServerRequest): ServerResponse {
        scope.launch {
            leaderboardRatingsUpdateService.updateRankedRatingsByRank(1000, 100).collect { }
            println("${Instant.now()}: done")
        }
        return ServerResponse.ok().bodyValueAndAwait(true)
    }
}