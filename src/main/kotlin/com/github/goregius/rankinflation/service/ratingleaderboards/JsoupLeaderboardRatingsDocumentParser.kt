package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import com.github.goregius.rankinflation.util.PlaylistMappings
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service

@Service
class JsoupLeaderboardRatingsDocumentParser :
    LeaderboardRatingsDocumentParser {
    override fun parse(html: String): List<ELeaderboardRating> {
        val document = Jsoup.parse(html)
        val ratingsTableBody = document.select(
            "body > div.container.content-container > div:nth-child(1) > " +
                "div.trn-container > div > div.col-md-8 > table > tbody"
        ).first()
        val ratingsRows = ratingsTableBody.children().drop(1)
        return ratingsRows.mapNotNull {
            parseRowToRankedRatingOrNull(it)
        }
    }

    private fun parseRowToRankedRatingOrNull(row: Element): ELeaderboardRating? {
        val cells = row.children()
        val rank = cells.getOrNull(0)?.text()?.toIntOrNull() ?: return null
        val gamer = cells.getOrNull(1)
            ?.select("a:nth-child(2)")?.text() ?: return null
        val rating = cells.getOrNull(2)
            ?.select("div")?.text()?.filter { it.isDigit() }?.toIntOrNull()
            ?: return null
        val games = cells.getOrNull(3)
            ?.text()?.toIntOrNull() ?: return null
        return ELeaderboardRating(
            id = null,
            gamer = gamer,
            rank = rank,
            rating = rating,
            games = games,
            playlist = PlaylistMappings.STANDARD_3S.id
        )
    }
}