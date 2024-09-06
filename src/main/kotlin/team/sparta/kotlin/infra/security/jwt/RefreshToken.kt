package team.sparta.kotlin.infra.security.jwt

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "refresh_token")
class RefreshToken (

    @Column(name = "member_id", nullable = false, unique = true)
    var memberId: Long,

    @Column(name = "refresh_token", nullable = false, unique = true)
    var refreshToken: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
){
    companion object{
        fun toEntity(memberId: Long,refreshToken: String): RefreshToken {
            return RefreshToken(memberId = memberId, refreshToken = refreshToken)
        }
    }
}