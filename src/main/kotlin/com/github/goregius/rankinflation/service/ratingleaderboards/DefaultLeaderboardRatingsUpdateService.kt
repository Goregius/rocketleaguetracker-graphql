package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.configuration.properties.RlTrackerProperties
import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import com.github.goregius.rankinflation.model.api.toELeaderboardRating
import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import com.github.goregius.rankinflation.repository.LeaderboardRatingRepository
import com.github.goregius.rankinflation.util.flowInBatches
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
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
        maxBatchSize: Int,
        playlist: Playlist
    ): Flow<ELeaderboardRating> =
        coroutineScope {
            val ratings = getRatings(pageRange, maxBatchSize, playlist)
                .toList().map { it.toELeaderboardRating() }
            leaderboardRatingRepository.deleteAll()
            leaderboardRatingRepository.saveAll(ratings).asFlow()
        }

    @FlowPreview
    override suspend fun updateRankedRatingsByRank(
        ranks: Int,
        maxBatchSize: Int,
        playlist: Playlist
    ): Flow<ELeaderboardRating> =
        coroutineScope {
            val ratings = getRatings(1..ranks / ranksPerPage + 1, maxBatchSize, playlist)
                .toList().map { it.toELeaderboardRating() }
            leaderboardRatingRepository.deleteAll()
            leaderboardRatingRepository.saveAll(ratings.subList(0, ranks)).asFlow()
        }

    @FlowPreview
    private suspend fun getRatings(pageRange: IntRange, maxBatchSize: Int, playlist: Playlist): Flow<LeaderboardRating> =
        retrieveHtmlInBatches(pageRange, maxBatchSize, playlist)
            .map { html ->
                leaderboardsDocumentParser.parse(html, playlist).asFlow()
            }.flattenMerge()

    private fun retrieveHtmlInBatches(pageRange: IntRange, maxBatchSize: Int, playlist: Playlist): Flow<String> =
        flowInBatches(pageRange, maxBatchSize) {
            retrieveHtml(it, playlist)
        }

    private suspend fun retrieveHtml(page: Int, playlist: Playlist): String = rlTrackerWebClient
        .get()
        .uri("/ranked-leaderboards/all/${playlist.number}?page=$page")
        .retrieve()
        .awaitBody()
}