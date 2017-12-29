package io.mikepoirier

import reactor.core.publisher.Mono
import java.util.*

private val RANDOM = Random()

fun generateUUID(): Mono<String> {
    return Mono.just(UUID.randomUUID())
        .map { it.toString() }
}

fun randomInt(upperBound: Int): Mono<Int> {
    return Mono.just(upperBound)
        .map { RANDOM.nextInt(it) }
}