package com.github.goregius.rankinflation.graphql.query

import com.expediagroup.graphql.spring.operations.Query
import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.entity.toLeaderboardRating
import com.github.goregius.rankinflation.repository.LeaderboardRatingRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.stereotype.Component

@Component
class LeaderboardRatingQuery(val leaderboardRatingRepository: LeaderboardRatingRepository) : Query {
    @Suppress("unused")
    suspend fun leaderboardRatings(
        playlist: Playlist?,
        offset: Long? = null,
        limit: Long? = null
    ): List<LeaderboardRating> =
        leaderboardRatingRepository
            .findAllByPlaylistOffsetPaginatedOrderByRank(
                playlist = playlist?.number ?: Playlist.RankedStandard.number,
                offset = offset ?: 0,
                limit = limit
            )
            .asFlow()
            .map { it.toLeaderboardRating() }
            .toList()
}