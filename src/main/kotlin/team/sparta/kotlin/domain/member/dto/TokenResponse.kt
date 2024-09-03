package team.sparta.kotlin.domain.member.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)