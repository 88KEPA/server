package com.kepa.application.user.trainer

import com.kepa.application.user.AccountReadService
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.dto.response.DetailInfo
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["[TRAINER] 트레이너 정보 API"])
@RequestMapping("/api/trainer/info")
class TrainerInfoController(
    private val accountReadService: AccountReadService,
    private val trainerWriteService: TrainerWriteService,
) {
    @ApiOperation(value = "사용자 정보 상세보기")
    @GetMapping("/info")
    fun getDetailInfo(@LoginUser loginUserInfo: LoginUserInfo): DetailInfo {
        return DetailInfo.create(accountReadService.getDetailInfo(id = loginUserInfo.id))
    }
}