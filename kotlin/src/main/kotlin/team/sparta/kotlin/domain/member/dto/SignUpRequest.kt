package team.sparta.kotlin.domain.member.dto

import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.kotlin.domain.member.entity.Member

data class SignUpRequest(
    val username: String,
    val password: String,
    val nickname: String,
) {
    companion object {
        fun toEntity(request: SignUpRequest, passwordEncoder: PasswordEncoder): Member {
            return Member(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                nickname = request.nickname,
            )
        }
    }
}
