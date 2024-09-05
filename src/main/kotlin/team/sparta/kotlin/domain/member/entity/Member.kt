package team.sparta.kotlin.domain.member.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class Member(

    @Column(name = "username", length = 50)
    var username: String,

    @Column(name = "password", length = 60)
    var password: String,

    @Column(name = "nickname", length = 50)
    var nickname: String,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "member_roles",
        joinColumns = [JoinColumn(name = "member_id")]
    )
    @Column(name = "role")
    val memberRoles: List<MemberRole> = listOf(MemberRole.MEMBER),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
){
}