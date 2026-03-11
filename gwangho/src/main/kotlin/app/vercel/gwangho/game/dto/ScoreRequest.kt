package app.vercel.gwangho.game.dto

data class ScoreRequest(
    val userName: String,
    val gameName: String,
    val gameCode: Long,
    val score: Double
)