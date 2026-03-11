package app.vercel.gwangho.game.controller

import app.vercel.gwangho.game.dto.RankingResponse
import app.vercel.gwangho.game.dto.ScoreRequest
import app.vercel.gwangho.game.service.RankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RankController(private val rankService: RankService) {

    @PostMapping("/postScore")
    fun postScore(@RequestBody request: ScoreRequest) {
        rankService.postScore(
            userName = request.userName,
            gameName = request.gameName,
            gameCode = request.gameCode,
            score = request.score
        )
    }

    @GetMapping("/getRanking")
    fun getRanking(@RequestParam gameCode: Long): RankingResponse {
        return rankService.getRanking(gameCode)
    }
}
