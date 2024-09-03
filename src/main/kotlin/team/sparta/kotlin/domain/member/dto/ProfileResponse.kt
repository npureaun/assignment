package team.sparta.kotlin.domain.member.dto

import team.sparta.kotlin.domain.member.entity.Member

data class ProfileResponse(
    val username: String,
    val nickname: String,
    val authorities: List<String>,
) {
    companion object {
        fun from(profile: Member): ProfileResponse {
            return ProfileResponse(
                username = profile.username,
                nickname = profile.nickname,
                authorities = profile.memberRoles.map { role -> role.name }
            )
        }
    }
}