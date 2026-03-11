package app.vercel.gwangho.game.service

import app.vercel.gwangho.game.dto.GameRanking
import app.vercel.gwangho.game.dto.RankingResponse
import app.vercel.gwangho.utils.jsonLoad
import app.vercel.gwangho.utils.jsonSave
import org.springframework.stereotype.Service

@Service
class RankService {
    fun postScore(userName: String, gameName: String, gameCode: Long, score: Double) {
        val rankingList = loadRankingData().toMutableList()
        val currentRanking = rankingList
            .firstOrNull { it.gameCode == gameCode }

        val updatedScores = (currentRanking?.rankingData.orEmpty() + GameRanking(userName, 0, score))
            .sortedByDescending { it.score }
            .mapIndexed { index, ranking ->
                GameRanking(
                    userName = ranking.userName,
                    ranking = index + 1,
                    score = ranking.score
                )
            }

        val updatedRanking = RankingResponse(
            gameName = gameName,
            gameCode = gameCode,
            rankingData = updatedScores
        )

        if (currentRanking == null) {
            rankingList.add(updatedRanking)
        } else {
            val currentIndex = rankingList.indexOfFirst { it.gameCode == gameCode }
            rankingList[currentIndex] = updatedRanking
        }

        jsonSave("rankData", rankingList)
    }

    fun getRanking(gameCode: Long): RankingResponse {
        return loadRankingData()
            .firstOrNull { it.gameCode == gameCode }
            ?: RankingResponse(
                gameName = "",
                gameCode = gameCode,
                rankingData = emptyList()
            )
    }

    private fun loadRankingData(): List<RankingResponse> {
        return try {
            jsonLoad("rankData")
        } catch (_: IllegalArgumentException) {
            emptyList()
        }
    }
}
