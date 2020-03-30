package com.github.goregius.rankinflation.model.entity

import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.api.playlist
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "LeaderboardRating")
class ELeaderboardRating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var rank: Int,
    var gamer: String,
    var rating: Int,
    var games: Int,
    var playlist: Int
)

fun ELeaderboardRating.toLeaderboardRating() = LeaderboardRating(
    id,
    rank,
    gamer,
    rating,
    games,
    playlist.playlist ?: Playlist.RankedStandard
)