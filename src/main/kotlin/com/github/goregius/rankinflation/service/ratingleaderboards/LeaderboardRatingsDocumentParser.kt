package com.github.goregius.rankinflation.service.ratingleaderboards

import com.github.goregius.rankinflation.model.entity.ELeaderboardRating
import com.github.goregius.rankinflation.service.DocumentParser

interface LeaderboardRatingsDocumentParser : DocumentParser<List<ELeaderboardRating>>
