package app.vercel.gwangho.websocket.service

import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Service
class WebSocketService {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    fun connect(session: WebSocketSession) {
        sessions[session.id] = session
    }

    fun disconnect(session: WebSocketSession) {
        sessions.remove(session.id)
    }

    fun broadcast(message: String) {
        sessions.values
            .filter { it.isOpen }
            .forEach { it.sendMessage(TextMessage(message)) }
    }

    fun send(session: WebSocketSession, message: String) {
        if (session.isOpen) {
            session.sendMessage(TextMessage(message))
        }
    }

    fun sessionCount(): Int {
        return sessions.size
    }
}
