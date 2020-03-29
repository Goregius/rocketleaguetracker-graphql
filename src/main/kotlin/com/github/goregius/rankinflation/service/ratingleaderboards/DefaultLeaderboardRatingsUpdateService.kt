package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.configuration.properties.RlTrackerProperties
import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import com.github.goregius.rankinflation.repository.LeaderboardRatingRepository
import com.github.goregius.rankinflation.util.flowInBatches
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class DefaultLeaderboardRatingsUpdateService(
    val leaderboardRatingRepository: LeaderboardRatingRepository,
    val rlTrackerWebClient: WebClient,
    val leaderboardsDocumentParser: LeaderboardRatingsDocumentParser,
    properties: RlTrackerProperties
) : LeaderboardRatingsUpdateService {
    val ranksPerPage = properties.page.rankedRatingLeaderboards.ranksPerPage

    @FlowPreview
    override suspend fun updateRankedRatingsByPage(
        pageRange: IntRange,
        maxBatchSize: Int
    ): Flow<ELeaderboardRating> =
        coroutineScope {
            val ratings = getRatings(pageRange, maxBatchSize).toList()
            leaderboardRatingRepository.deleteAll().awaitFirstOrNull()
            leaderboardRatingRepository.saveAll(ratings).asFlow()
        }

    @FlowPreview
    override suspend fun updateRankedRatingsByRank(
        ranks: Int,
        maxBatchSize: Int
    ): Flow<ELeaderboardRating> =
        coroutineScope {
            val ratings = getRatings(1..ranks / ranksPerPage + 1, maxBatchSize).toList()
            leaderboardRatingRepository.deleteAll().awaitFirstOrNull()
            leaderboardRatingRepository.saveAll(ratings.subList(0, ranks)).asFlow()
        }

    @FlowPreview
    private suspend fun getRatings(pageRange: IntRange, maxBatchSize: Int) =
        retrieveHtmlInBatches(pageRange, maxBatchSize)
            .map { html ->
                leaderboardsDocumentParser.parse(html).asFlow()
            }.flattenMerge()

    private fun retrieveHtmlInBatches(pageRange: IntRange, maxBatchSize: Int): Flow<String> =
        flowInBatches(pageRange, maxBatchSize) {
            retrieveHtml(it)
        }

    private suspend fun retrieveHtml(page: Int): String = rlTrackerWebClient
        .get()
        .uri("/ranked-leaderboards/all/13?page=$page")
        .retrieve()
        .awaitBody()
}