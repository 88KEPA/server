package com.kepa.externalapi.dto

import java.security.SecureRandom

class RandomNumber {
    companion object {
        fun create(): Int {
            var secureRandom = SecureRandom();
            secureRandom.setSeed(100_000)
            return secureRandom.nextInt(999_999)
        }
    }
}