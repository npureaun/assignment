package team.sparta.kotlin.domain.member.dto

data class LoginRequest(
    val username: String,
    val password: String,
)