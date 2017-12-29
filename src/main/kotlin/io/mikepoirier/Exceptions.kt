package io.mikepoirier

import org.springframework.http.HttpStatus


data class WebException(
    override val message: String,
    val errorCode: HttpStatus,
    val body: Any = mapOf(Pair("message", message))
) : Throwable(message)
