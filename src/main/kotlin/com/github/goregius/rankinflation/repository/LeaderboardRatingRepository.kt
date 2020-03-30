package com.github.goregius.rankinflation.repository

import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface LeaderboardRatingRepository : PagingAndSortingRepository<ELeaderboardRating, Long> {
    @Query(
        "select * from leaderboard_rating where playlist = :playlist order by rank offset :offset limit NULLIF(:limit, -1)",
        nativeQuery = true
    )
    fun findAllByPlaylistOrderByRankOffsetLimit(playlist: Int, offset: Long, limit: Long): List<ELeaderboardRating>
}