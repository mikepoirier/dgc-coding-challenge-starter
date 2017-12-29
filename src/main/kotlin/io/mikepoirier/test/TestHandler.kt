package io.mikepoirier.test

import io.mikepoirier.generateUUID
import io.mikepoirier.web.RequestResponseLogger
import io.mikepoirier.web.createJsonResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class TestHandler(val testService: TestService, val requestResponseLogger: RequestResponseLogger) {

    fun handlePost(req: ServerRequest): Mono<ServerResponse> {

        val sessionMono = generateUUID()

        val body = req.bodyToMono<TestRequest>()
            .doOnNext(requestResponseLogger.logRequestJson(sessionMono))
            .map { it.name }
            .flatMap { testService.getMessage(it) }
            .map { TestResponse(it) }
            .doOnNext(requestResponseLogger.logResponseJson(sessionMono))

        return createJsonResponse(HttpStatus.OK, body)
    }

    fun handleGet(req: ServerRequest): Mono<ServerResponse> {

        val sessionMono = generateUUID()

        val response = TestResponse("Hello, World!").toMono()
            .doOnNext(requestResponseLogger.logResponseJson(sessionMono))

        return createJsonResponse(HttpStatus.OK, response)
    }
}

