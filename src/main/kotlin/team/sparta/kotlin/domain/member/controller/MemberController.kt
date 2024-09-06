package team.sparta.kotlin.domain.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.sparta.kotlin.domain.member.dto.LoginRequest
import team.sparta.kotlin.domain.member.dto.ProfileResponse
import team.sparta.kotlin.domain.member.dto.SignUpRequest
import team.sparta.kotlin.domain.member.service.MemberService
import team.sparta.kotlin.infra.security.jwt.config.UserPrincipal

@RestController
class MemberController(
    private val memberService: MemberService,
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<ProfileResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.signup(request))
    }

    @PostMapping("/sign")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<String> {
        val token = memberService.login(request)

        return ResponseEntity.status(HttpStatus.OK)
            .body(token.accessToken)
    }

    @PostMapping("logout")
    fun logout(@AuthenticationPrincipal principal: UserPrincipal): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(memberService.logout(principal))
    }

    @DeleteMapping("/deactivate")
    fun deactivate(@AuthenticationPrincipal principal: UserPrincipal): ResponseEntity<Void> {
        memberService.deactivate(principal)

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PostMapping("/refresh-token")
    fun refreshAccessToken(
        @RequestParam accessToken: String,
    ): ResponseEntity<String> {
        val tokenResponse = memberService.refreshAccessToken(accessToken)
        return ResponseEntity.ok().body(tokenResponse.accessToken)
    }
}