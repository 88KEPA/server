package com.kepa.application.trainer.dto.request

import java.security.SecureRandom
import java.util.Random

class RandomNumber {
    companion object {
        fun create(): Int {
            var secureRandom = SecureRandom();
            secureRandom.setSeed(100_000)
            return secureRandom.nextInt(999_999)
        }
    }
}