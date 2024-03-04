    package com.kepa.application.community.dto.request

    import org.springframework.web.multipart.MultipartFile

    /**
 * packageName    : com.kepa.application.community.dto.request
 * fileName       : CommunityRequest
 * author         : hoewoonjeong
 * date           : 3/1/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/1/24        hoewoonjeong               최초 생성
 */

data class CommunityCreate(
    val title: String,
    val content: String,
)