package team.sparta.kotlin.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import team.sparta.kotlin.domain.member.entity.MemberRole
import team.sparta.kotlin.infra.security.jwt.config.JwtTokenManager
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@ActiveProfiles("test")
class JwtTest {

    @Value("\${auth.jwt.secret}")
    private lateinit var secret: String

    @Autowired
    private lateinit var jwtTokenManager: JwtTokenManager

    @Test
    fun `토큰 안에 값이 제대로 들어왔는지 테스트하는 함수`() {
        val accessToken = jwtTokenManager.generateTokenResponse(
            memberId = 1L,
            memberRole = MemberRole.MEMBER
        ).accessToken

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken)

        assertEquals("1", claims.payload.subject)
        assertEquals("MEMBER", claims.payload["memberRole", String::class.java])
    }

    @Test
    fun `토큰이 정상적으로 검증이 되는지 테스트하는 함수`() {
        val accessToken = jwtTokenManager.generateTokenResponse(
            memberId = 2L,
            memberRole = MemberRole.ADMIN
        ).accessToken

        val result = jwtTokenManager.validateToken(accessToken)

        assertTrue(result.isSuccess)
    }
}

