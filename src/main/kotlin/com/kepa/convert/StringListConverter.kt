package com.kepa.convert

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.kepa.common.exception.ExceptionCode
import com.kepa.common.exception.KepaException
import javax.persistence.AttributeConverter

/**
 * packageName    : com.kepa.convert
 * fileName       : StringListConverter
 * author         : hoewoonjeong
 * date           : 3/4/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/24        hoewoonjeong               최초 생성
 */
class StringListConverter(
    val objectMapper: ObjectMapper,
) : AttributeConverter<List<String>, String> {
    override fun convertToDatabaseColumn(listString: List<String>?): String {
        listString.let {
            try {
                return objectMapper.writeValueAsString(listString)
            }catch (e: JsonProcessingException) {
                throw KepaException(ExceptionCode.BAD_REQUEST_DATA)
            }
        }
    }

    override fun convertToEntityAttribute(string: String?): List<String> {
        try {
            return objectMapper.readValue(string, List::class.java) as List<String>
        }catch (e: JsonProcessingException) {
            throw KepaException(ExceptionCode.BAD_REQUEST_DATA)
        }
    }
}