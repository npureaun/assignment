package team.sparta.kotlin.domain.member.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import team.sparta.kotlin.domain.member.dto.LoginRequest
import team.sparta.kotlin.domain.member.dto.ProfileResponse
import team.sparta.kotlin.domain.member.dto.SignUpRequest
import team.sparta.kotlin.domain.member.service.MemberService
import team.sparta.kotlin.infra.security.jwt.UserPrincipal

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

        val cookie = ResponseCookie.from("refreshToken", token.refreshToken)
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .maxAge(7 * 24 * 60 * 60)
            .path("/")
            .build()

        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(token.accessToken)
    }

    @PostMapping("/auth/logout")
    fun logout(): ResponseEntity<Void> {
        val deleteCookie = ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .maxAge(0)
            .path("/")
            .build()

        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
            .build()
    }

    @DeleteMapping("/deactivate")
    fun deactivate(@AuthenticationPrincipal principal: UserPrincipal): ResponseEntity<Void> {
        memberService.deactivate(principal)

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PostMapping("/auth/refresh-token")
    fun refreshAccessToken(
        @CookieValue("refreshToken") refreshToken: String
    ): ResponseEntity<String> {
        val tokenResponse = memberService.refreshAccessToken(refreshToken)
        return ResponseEntity.ok().body(tokenResponse.accessToken)
    }
}