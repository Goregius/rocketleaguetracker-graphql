package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import kotlinx.coroutines.flow.Flow

interface LeaderboardRatingsUpdateService {
    suspend fun updateRankedRatingsByPage(
        pageRange: IntRange,
        maxBatchSize: Int,
        playlist: Playlist
    ): Flow<ELeaderboardRating>

    suspend fun updateRankedRatingsByRank(
        ranks: Int,
        maxBatchSize: Int,
        playlist: Playlist
    ): Flow<ELeaderboardRating>
}

