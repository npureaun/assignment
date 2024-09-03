package team.sparta.kotlin.domain.member.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import team.sparta.kotlin.domain.member.dto.LoginRequest
import team.sparta.kotlin.domain.member.dto.ProfileResponse
import team.sparta.kotlin.domain.member.dto.SignUpRequest
import team.sparta.kotlin.domain.member.dto.TokenResponse
import team.sparta.kotlin.domain.member.entity.MemberRole
import team.sparta.kotlin.domain.member.repository.MemberRepository
import team.sparta.kotlin.infra.security.jwt.JwtTokenManager
import team.sparta.kotlin.infra.security.jwt.UserPrincipal

@Service
class MemberServiceImpl @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenManager: JwtTokenManager,
) : MemberService {

    override fun signup(request: SignUpRequest): ProfileResponse {
        check(!memberRepository.existsByNickname(request.nickname)) { "이미 존재하는 닉네임" }
        val member = SignUpRequest.toEntity(request, passwordEncoder)
        memberRepository.saveAndFlush(member)
        return ProfileResponse.from(member)
    }

    override fun login(request: LoginRequest): TokenResponse {
        val loginMember = memberRepository.findByUsername(request.username) ?: throw IllegalStateException("유저정보 없음")
        check(
            passwordEncoder.matches(
                request.password,
                loginMember.password
            )
        ) { "비밀번호가 맞지 않음" }
        val tokens = jwtTokenManager.generateTokenResponse(loginMember.id!!, MemberRole.MEMBER)

        return tokens
    }

    override fun refreshAccessToken(refreshToken: String): TokenResponse {
        return jwtTokenManager.validateToken(refreshToken).fold(
            onSuccess = {
                val tokens = jwtTokenManager.generateTokenResponse(
                    memberId = it.payload.subject.toLong(),
                    memberRole = MemberRole.valueOf(it.payload.get("memberRole", String::class.java))
                )
                tokens
            },
            onFailure = { throw IllegalStateException(" 토큰이 검증되지않음") }
        )
    }

    override fun deactivate(principal: UserPrincipal) {
        memberRepository.deleteById(principal.memberId)
    }
}
