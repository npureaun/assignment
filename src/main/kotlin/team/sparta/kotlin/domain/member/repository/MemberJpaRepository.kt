package team.sparta.kotlin.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.kotlin.domain.member.entity.Member

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByNickname(nickname: String): Boolean
    fun findByUsername(username: String): Member?
}