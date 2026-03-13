package app.vercel.gwangho.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.websocket")
data class WebSocketProperties(
    var allowedOrigins: List<String> = listOf(
        "https://gwangho.vercel.app",
        "http://localhost:3000",
        "http://localhost:5173"
    ),
    var authToken: String? = null
)
