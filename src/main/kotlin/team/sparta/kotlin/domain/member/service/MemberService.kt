package team.sparta.kotlin.domain.member.service

import team.sparta.kotlin.domain.member.dto.LoginRequest
import team.sparta.kotlin.domain.member.dto.ProfileResponse
import team.sparta.kotlin.domain.member.dto.SignUpRequest
import team.sparta.kotlin.domain.member.dto.TokenResponse
import team.sparta.kotlin.infra.security.jwt.config.UserPrincipal

interface MemberService {
    fun signup(request: SignUpRequest): ProfileResponse

    fun login(request: LoginRequest): TokenResponse

    fun refreshAccessToken(accessToken: String): TokenResponse

    fun deactivate(principal: UserPrincipal)

    fun logout(principal: UserPrincipal)
}