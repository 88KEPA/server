package com.kepa.common.exception


class KepaException(
    val exceptionCode: ExceptionCode,
) : RuntimeException()