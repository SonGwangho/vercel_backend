package app.vercel.gwangho.config

import app.vercel.gwangho.websocket.controller.WebSocketAuthInterceptor
import app.vercel.gwangho.websocket.controller.WebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val webSocketHandler: WebSocketHandler,
    private val webSocketAuthInterceptor: WebSocketAuthInterceptor,
    private val webSocketProperties: WebSocketProperties
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSocketHandler, "/ws/chat")
            .addInterceptors(webSocketAuthInterceptor)
            .setAllowedOrigins(*webSocketProperties.allowedOrigins.toTypedArray())
    }
}
