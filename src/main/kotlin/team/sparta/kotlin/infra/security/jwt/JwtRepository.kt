package team.sparta.kotlin.infra.security.jwt

import org.springframework.data.jpa.repository.JpaRepository

interface JwtRepository : JpaRepository<RefreshToken, Long>{
    fun findByMemberId(memberId: Long): RefreshToken?
    fun deleteByMemberId(memberId: Long)
}