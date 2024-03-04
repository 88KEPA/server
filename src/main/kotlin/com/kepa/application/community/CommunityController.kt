package com.kepa.application.community

import com.kepa.application.community.dto.request.CommunityCreate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * packageName    : com.kepa.application.community
 * fileName       : CommunityController
 * author         : hoewoonjeong
 * date           : 3/1/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/1/24        hoewoonjeong               최초 생성
 */
@Api(tags = ["[TRAINER] 커뮤니티"])
@RequestMapping("/api/community")
@RestController
class CommunityController {


    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "글 등록")
    fun create(@RequestPart community: CommunityCreate, @RequestPart images: List<MultipartFile>) {
    }

}