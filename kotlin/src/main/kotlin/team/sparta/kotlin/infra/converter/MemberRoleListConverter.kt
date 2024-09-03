package team.sparta.kotlin.infra.converter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import team.sparta.kotlin.domain.member.entity.MemberRole

@Converter
class MemberRoleListConverter : AttributeConverter<List<MemberRole>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<MemberRole>?): String {
        return attribute?.let { objectMapper.writeValueAsString(it) } ?: "[]"
    }

    override fun convertToEntityAttribute(p0: String?): List<MemberRole> {
        TODO("Not yet implemented")
    }
}