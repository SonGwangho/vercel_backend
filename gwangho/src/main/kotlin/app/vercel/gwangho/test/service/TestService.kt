package app.vercel.gwangho.test.service

import org.springframework.stereotype.Service

@Service
class TestService {

    fun getTestMessage(): String {
        return "테스트 메세지"
    }
}
