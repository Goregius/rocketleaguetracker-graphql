package com.github.goregius.rankinflation.model.entity

import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.api.playlist
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("leaderboard_rating")
data class ELeaderboardRating(
    @Id
    val id: Long? = null,
    val rank: Int,
    val gamer: String,
    val rating: Int,
    val games: Int,
    val playlist: Int
)

fun ELeaderboardRating.toLeaderboardRating() = LeaderboardRating(
    id,
    rank,
    gamer,
    rating,
    games,
    playlist.playlist ?: Playlist.RankedStandard
)