package app.vercel.gwangho.game.dto

data class RankingResponse(
    val gameName: String,
    val gameCode: Long,
    val rankingData: List<GameRanking>
)

data class GameRanking (
    val userName: String,
    val ranking: Int,
    val score: Double
)