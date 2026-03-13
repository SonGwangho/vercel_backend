package app.vercel.gwangho.websocket.controller

import app.vercel.gwangho.websocket.dto.WebSocketMessageRequest
import app.vercel.gwangho.websocket.dto.WebSocketMessageResponse
import app.vercel.gwangho.websocket.service.WebSocketService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.time.OffsetDateTime

@Component
class WebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val webSocketService: WebSocketService
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        webSocketService.connect(session)
        val connectedAt = OffsetDateTime.now().toString()
        val response = WebSocketMessageResponse(
            type = "connect",
            sessionId = session.id,
            message = "connected",
            connectedAt = connectedAt,
            sessionCount = webSocketService.sessionCount()
        )
        webSocketService.send(session, objectMapper.writeValueAsString(response))
        broadcastSystemEvent(
            type = "join",
            session = session,
            message = "session joined"
        )
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        runCatching {
            objectMapper.readValue(message.payload, WebSocketMessageRequest::class.java)
        }.onSuccess { request ->
            when (request.type.lowercase()) {
                "ping" -> {
                    val response = WebSocketMessageResponse(
                        type = "pong",
                        sessionId = session.id,
                        message = "pong",
                        sentAt = OffsetDateTime.now().toString(),
                        sessionCount = webSocketService.sessionCount()
                    )
                    webSocketService.send(session, objectMapper.writeValueAsString(response))
                }

                "message" -> {
                    val response = WebSocketMessageResponse(
                        type = "message",
                        sessionId = session.id,
                        sender = request.sender ?: "anonymous",
                        message = request.message ?: "",
                        sentAt = OffsetDateTime.now().toString(),
                        sessionCount = webSocketService.sessionCount()
                    )
                    webSocketService.broadcast(objectMapper.writeValueAsString(response))
                }

                else -> {
                    sendError(session, "unsupported message type: ${request.type}")
                }
            }
        }.onFailure {
            sendError(session, "invalid payload")
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        if (session.isOpen) {
            session.close(CloseStatus.SERVER_ERROR)
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        webSocketService.disconnect(session)
        broadcastSystemEvent(
            type = "disconnect",
            session = session,
            message = "session disconnected"
        )
    }

    private fun broadcastSystemEvent(type: String, session: WebSocketSession, message: String) {
        val response = WebSocketMessageResponse(
            type = type,
            sessionId = session.id,
            message = message,
            sentAt = OffsetDateTime.now().toString(),
            sessionCount = webSocketService.sessionCount()
        )
        webSocketService.broadcast(objectMapper.writeValueAsString(response))
    }

    private fun sendError(session: WebSocketSession, message: String) {
        val response = WebSocketMessageResponse(
            type = "error",
            sessionId = session.id,
            message = message,
            sentAt = OffsetDateTime.now().toString()
        )
        webSocketService.send(session, objectMapper.writeValueAsString(response))
    }
}
