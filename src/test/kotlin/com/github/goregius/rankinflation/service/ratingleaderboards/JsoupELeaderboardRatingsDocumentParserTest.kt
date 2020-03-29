package com.github.goregius.rankinflation.service.ratingleaderboards

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.io.ClassPathResource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsoupELeaderboardRatingsDocumentParserTest {
    private val jsoupRankedRatingLeaderboardsDocumentParser =
        JsoupLeaderboardRatingsDocumentParser()

    private val html =
        ClassPathResource("test_pages/ranked_rating_leaderboards_standard.html")
            .inputStream.bufferedReader()
            .use { reader ->
                reader.readText()
            }

    private val result = jsoupRankedRatingLeaderboardsDocumentParser.parse(html)

    @Test
    fun `should have size of 100`() {
        result.size shouldBe 100
    }
}