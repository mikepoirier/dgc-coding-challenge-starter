package io.mikepoirier.web

import io.mikepoirier.WebException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
class ErrorHandler {

    fun handleGet(req: ServerRequest): Mono<ServerResponse> {
        val result = processRequest(req.queryParam("test"))

        return createJsonResponse(HttpStatus.OK, result)
    }

    fun processRequest(thing: Optional<String>): Mono<Map<String, String>> {
        return Mono.justOrEmpty(thing)
            .switchIfEmpty(defaultValue())
            .map { mapOf("body" to it) }
    }

    fun defaultValue(): Mono<String> {
        return Mono.error(WebException("Request is missing the query parameter 'test'", HttpStatus.BAD_REQUEST))
    }
}