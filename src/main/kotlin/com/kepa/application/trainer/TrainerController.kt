package com.kepa.application.trainer

import com.kepa.application.trainer.dto.request.TrainerJoin
import io.swagger.annotations.Api
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Api(tags = ["[TRAINER] 트레이너 입회 API"])
@RequestMapping("/api/trainer")
class TrainerController(
    private val trainerWriteService: TrainerWriteService,
) {

    @Operation(description = "트레이너 입회")
    @PostMapping
    fun create(@Valid @RequestBody trainerJoin: TrainerJoin) {
        trainerWriteService.join(trainerJoin);
    }

}