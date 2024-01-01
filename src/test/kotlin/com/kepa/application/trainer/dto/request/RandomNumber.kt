package com.kepa.application.trainer.dto.request

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import java.security.SecureRandom


class RandomNumber:StringSpec({

    "랜덤한 값 10만 이상인 값 생성한다"  {
        var secureRandom = SecureRandom();
        secureRandom.setSeed(100_000)
        secureRandom.nextInt(999999) shouldBeGreaterThan 100000
    }
})
