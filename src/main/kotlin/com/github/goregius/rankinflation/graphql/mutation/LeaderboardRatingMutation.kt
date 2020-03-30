package com.github.goregius.rankinflation.graphql.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import com.github.goregius.rankinflation.model.api.Playlist
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
    suspend fun refetchLeaderboardRatings(size: Int?, playlist: Playlist?) =
        UpdateResult(leaderboardRatingsUpdateService.updateRankedRatingsByRank(
            ranks = size ?: 1000,
            maxBatchSize = 10,
            playlist = playlist ?: Playlist.RankedStandard
        ).count())
}