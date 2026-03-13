package app.vercel.gwangho.websocket.dto

data class WebSocketMessageResponse(
    val type: String,
    val sessionId: String,
    val sender: String? = null,
    val message: String? = null,
    val connectedAt: String? = null,
    val sentAt: String? = null,
    val disconnectedAt: String? = null,
    val sessionCount: Int? = null
)
