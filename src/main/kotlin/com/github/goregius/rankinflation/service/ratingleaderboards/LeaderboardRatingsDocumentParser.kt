package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import com.github.goregius.rankinflation.service.DocumentParser

interface LeaderboardRatingsDocumentParser {
    fun parse(html: String, playlist: Playlist): List<LeaderboardRating>
}
