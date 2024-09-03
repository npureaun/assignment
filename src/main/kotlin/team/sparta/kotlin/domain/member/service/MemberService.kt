package team.sparta.kotlin.domain.member.service

import org.springframework.web.multipart.MultipartFile
import team.sparta.kotlin.domain.member.dto.LoginRequest
import team.sparta.kotlin.domain.member.dto.ProfileResponse
import team.sparta.kotlin.domain.member.dto.SignUpRequest
import team.sparta.kotlin.domain.member.dto.TokenResponse
import team.sparta.kotlin.infra.security.jwt.UserPrincipal

interface MemberService {
    fun signup(request: SignUpRequest): ProfileResponse

    fun login(request: LoginRequest): TokenResponse

    fun refreshAccessToken(refreshToken: String): TokenResponse

    fun deactivate(principal: UserPrincipal)
}