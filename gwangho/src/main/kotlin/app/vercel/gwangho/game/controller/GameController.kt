package app.vercel.gwangho.game.controller

import app.vercel.gwangho.game.service.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(private val gameService: GameService) {
}
