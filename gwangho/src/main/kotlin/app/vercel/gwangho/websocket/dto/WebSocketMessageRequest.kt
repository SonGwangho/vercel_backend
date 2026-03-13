package app.vercel.gwangho.websocket.dto

data class WebSocketMessageRequest(
    val type: String = "message",
    val sender: String? = null,
    val message: String? = null
)
