package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.api.LeaderboardRating
import com.github.goregius.rankinflation.model.api.Playlist
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service

@Service
class JsoupLeaderboardRatingsDocumentParser : LeaderboardRatingsDocumentParser {
    override fun parse(html: String, playlist: Playlist): List<LeaderboardRating> {
        val document = Jsoup.parse(html)
        val ratingsTableBody = document.select(
            "body > div.container.content-container > div:nth-child(1) > " +
                "div.trn-container > div > div.col-md-8 > table > tbody"
        ).first()
        val ratingsRows = ratingsTableBody.children().drop(1)
        return ratingsRows.mapNotNull {
            parseRowToRankedRatingOrNull(it, playlist)
        }
    }

    private fun parseRowToRankedRatingOrNull(row: Element, playlist: Playlist): LeaderboardRating? {
        val cells = row.children()
        val rank = cells.getOrNull(0)?.text()?.toIntOrNull() ?: return null
        val gamer = cells.getOrNull(1)
            ?.select("a:nth-child(2)")?.text() ?: return null
        val rating = cells.getOrNull(2)
            ?.select("div")?.text()?.filter { it.isDigit() }?.toIntOrNull()
            ?: return null
        val games = cells.getOrNull(3)
            ?.text()?.toIntOrNull() ?: return null
        return LeaderboardRating(
            id = null,
            gamer = gamer,
            rank = rank,
            rating = rating,
            games = games,
            playlist = playlist
        )
    }
}