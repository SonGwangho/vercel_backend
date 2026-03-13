package app.vercel.gwangho.health.controller

import app.vercel.gwangho.websocket.service.WebSocketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
class HealthController(
    private val webSocketService: WebSocketService
) {

    @GetMapping("/health")
    fun health(): Map<String, Any> {
        return mapOf(
            "status" to "UP",
            "timestamp" to OffsetDateTime.now().toString(),
            "websocket" to mapOf(
                "endpoint" to "/ws/chat",
                "sessionCount" to webSocketService.sessionCount()
            )
        )
    }
}
