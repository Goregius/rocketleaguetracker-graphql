package com.github.goregius.rankinflation.repository

import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface LeaderboardRatingRepository : ReactiveCrudRepository<ELeaderboardRating, Long> {
    @Query("select * from leaderboard_rating where playlist = :playlist order by rank offset :offset limit :limit")
    fun findAllByPlaylistOffsetPaginatedOrderByRank(playlist: Int, offset: Long, limit: Long?): Flux<ELeaderboardRating>
}