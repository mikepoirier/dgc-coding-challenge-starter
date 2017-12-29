package io.mikepoirier.web

import io.mikepoirier.logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ChallengeHandler {

    private val log = logger<ChallengeHandler>()

    fun handleGet(req: ServerRequest): Mono<ServerResponse> {

        val bodyMono = Mono.empty<Void>()

        return createJsonResponse(HttpStatus.OK, bodyMono)
    }

    fun handlePost(req: ServerRequest): Mono<ServerResponse> {
        val bodyMono = Mono.empty<Void>()

        return createJsonResponse(HttpStatus.OK, bodyMono)
    }

    fun handlePut(req: ServerRequest): Mono<ServerResponse> {
        val bodyMono = Mono.empty<Void>()

        return createJsonResponse(HttpStatus.OK, bodyMono)
    }

    fun handlePatch(req: ServerRequest): Mono<ServerResponse> {
        val bodyMono = Mono.empty<Void>()

        return createJsonResponse(HttpStatus.OK, bodyMono)
    }

    fun handleDelete(req: ServerRequest): Mono<ServerResponse> {
        val bodyMono = Mono.empty<Void>()

        return createJsonResponse(HttpStatus.OK, bodyMono)
    }
}

