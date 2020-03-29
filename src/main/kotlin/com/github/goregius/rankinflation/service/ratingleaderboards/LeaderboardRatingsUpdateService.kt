package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import kotlinx.coroutines.flow.Flow

interface LeaderboardRatingsUpdateService {
    suspend fun updateRankedRatingsByPage(pageRange: IntRange, maxBatchSize: Int): Flow<ELeaderboardRating>
    suspend fun updateRankedRatingsByRank(ranks: Int, maxBatchSize: Int): Flow<ELeaderboardRating>
}

