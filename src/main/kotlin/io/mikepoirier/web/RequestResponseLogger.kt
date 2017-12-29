package io.mikepoirier.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.mikepoirier.logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers
import reactor.util.function.Tuple2

@Component
class RequestResponseLogger(val objectMapper: ObjectMapper) {
    private val log = logger<RequestResponseLogger>()

    fun logRequestJson(sessionIdMono: Mono<String>): (Any) -> Unit {
        return { request ->
            request.toMono()
                .transform(writeJsonAndZipSessionId(sessionIdMono))
                .subscribe {
                    val sessionId = it.t2
                    val json = it.t1
                    log.info("($sessionId) Request: $json")
                }
        }
    }

    fun logResponseJson(sessionIdMono: Mono<String>): (Any) -> Unit {
        return { response ->
            response.toMono()
                .transform(writeJsonAndZipSessionId(sessionIdMono))
                .subscribe {
                    val sessionId = it.t2
                    val json = it.t1
                    log.info("($sessionId) Response: $json")
                }
        }
    }

    private fun writeJsonAndZipSessionId(sessionIdMono: Mono<String>): (Mono<Any>) -> Mono<Tuple2<String, String>> {
        return { mono ->
            mono.subscribeOn(Schedulers.parallel())
                .map { objectMapper.writeValueAsString(it) }
                .zipWith(sessionIdMono)
        }
    }
}