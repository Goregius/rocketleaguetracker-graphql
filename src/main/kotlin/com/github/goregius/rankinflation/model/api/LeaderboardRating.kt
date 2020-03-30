package com.github.goregius.rankinflation.model.api

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.github.goregius.rankinflation.model.entity.ELeaderboardRating

enum class Playlist(val number: Int) {
    Unranked(0),
    RankedDuel(10),
    RankedDoubles(11),
    RankedSoloStandard(12),
    RankedStandard(13),
    Hoops(27),
    Rumble(28),
    Dropshot(29),
    Snowday(30)
}

val Number.playlist: Playlist?
    get() = when (this) {
        0 -> Playlist.Unranked
        10 -> Playlist.RankedDuel
        11 -> Playlist.RankedDoubles
        12 -> Playlist.RankedSoloStandard
        13 -> Playlist.RankedStandard
        27 -> Playlist.Hoops
        28 -> Playlist.Rumble
        29 -> Playlist.Dropshot
        30 -> Playlist.Snowday
        else -> null
    }

data class LeaderboardRating(
    @GraphQLIgnore
    val id: Long?,
    val rank: Int,
    val gamer: String,
    val rating: Int,
    val games: Int,
    val playlist: Playlist
)

fun LeaderboardRating.toELeaderboardRating() = ELeaderboardRating(
    id,
    rank,
    gamer,
    rating,
    games,
    playlist.number
)