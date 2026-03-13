package app.vercel.gwangho.websocket.controller

import app.vercel.gwangho.config.WebSocketProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor

@Component
class WebSocketAuthInterceptor(
    private val webSocketProperties: WebSocketProperties
) : HandshakeInterceptor {

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val servletRequest = (request as? ServletServerHttpRequest)?.servletRequest ?: return false
        val servletResponse = (response as? ServletServerHttpResponse)?.servletResponse ?: return false
        val origin = request.headers.origin

        if (!isAllowedOrigin(origin)) {
            servletResponse.status = HttpStatus.FORBIDDEN.value()
            return false
        }

        if (!isAuthorized(servletRequest)) {
            servletResponse.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }

        attributes["clientOrigin"] = origin.orEmpty()
        attributes["clientToken"] = extractToken(servletRequest).orEmpty()

        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?
    ) {
    }

    private fun isAllowedOrigin(origin: String?): Boolean {
        if (origin.isNullOrBlank()) {
            return false
        }

        return webSocketProperties.allowedOrigins.contains(origin)
    }

    private fun isAuthorized(request: HttpServletRequest): Boolean {
        val requiredToken = webSocketProperties.authToken
        if (requiredToken.isNullOrBlank()) {
            return true
        }

        val providedToken = extractToken(request)
        return providedToken == requiredToken
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        if (!authorizationHeader.isNullOrBlank() && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.removePrefix("Bearer ").trim()
        }

        val headerToken = request.getHeader("X-WebSocket-Token")
        if (!headerToken.isNullOrBlank()) {
            return headerToken.trim()
        }

        return request.getParameter("token")?.trim()
    }
}
