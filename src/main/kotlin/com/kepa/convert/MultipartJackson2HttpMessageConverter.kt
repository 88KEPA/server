package com.kepa.convert

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import java.lang.reflect.Type

/**
 * packageName    : com.kepa.convert
 * fileName       : MultipartJackson2HttpMessageConverter
 * author         : hoewoonjeong
 * date           : 3/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/17/24        hoewoonjeong               최초 생성
 */
@Component
class MultipartJackson2HttpMessageConverter(
    private val objectMapper: ObjectMapper) :
    AbstractJackson2HttpMessageConverter(objectMapper, MediaType.APPLICATION_OCTET_STREAM) {

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }

    override fun canWrite(type: Type?, clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }
    override fun canWrite(mediaType: MediaType?): Boolean {
        return false
    }
}