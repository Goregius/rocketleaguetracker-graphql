package com.github.goregius.rankinflation.graphql.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import com.github.goregius.rankinflation.service.ratingleaderboards.LeaderboardRatingsUpdateService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import org.springframework.stereotype.Component

data class UpdateResult(val rowsUpdated: Int)


@Component
class LeaderboardRatingMutation(
    val leaderboardRatingsUpdateService: LeaderboardRatingsUpdateService
) : Mutation {
    @ExperimentalCoroutinesApi
    @Suppress("unused")
    suspend fun refetchLeaderboardRatings(size: Int?) =
        UpdateResult(leaderboardRatingsUpdateService.updateRankedRatingsByRank(size ?: 1000, 10).count())
}