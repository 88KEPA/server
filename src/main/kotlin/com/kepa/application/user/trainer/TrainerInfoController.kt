package com.kepa.application.user.trainer

import UpdateInfo
import com.kepa.application.user.AccountReadService
import com.kepa.application.user.dto.request.LoginUserInfo
import com.kepa.application.user.dto.response.DetailInfo
import com.kepa.domain.user.annotation.LoginUser
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["[TRAINER] 트레이너 정보 API"])
@RequestMapping("/api/trainer/info")
@Secured(value = ["ROLE_ADMIN","ROLE_TRAINER","ROLE_SUPER_ADMIN"])
class TrainerInfoController(
    private val accountReadService: AccountReadService,
    private val trainerWriteService: TrainerWriteService,
) {
    @ApiOperation(value = "사용자 정보 상세보기")
    @GetMapping
    fun getDetailInfo(@LoginUser loginUserInfo: LoginUserInfo): DetailInfo {
        return DetailInfo.create(accountReadService.getDetailInfo(id = loginUserInfo.id))
    }

    @ApiOperation(value = "정보 수정하기")
    @PutMapping
    fun update(@LoginUser loginUserInfo: LoginUserInfo, @RequestBody updateInfo: UpdateInfo) {
        trainerWriteService.update(
            accountId =  loginUserInfo.id,
            email =  updateInfo.email,
            address = updateInfo.address,
            addressMeta = updateInfo.addressMeta,
            addressDetail = updateInfo.addressDetail,
            phone = updateInfo.phone,
        )
    }

}