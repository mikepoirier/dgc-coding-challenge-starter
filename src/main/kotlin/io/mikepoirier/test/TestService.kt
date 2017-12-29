package io.mikepoirier.test

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class TestService {

    fun getMessage(name: String = "World"): Mono<String> {
        return name.toMono()
            .map { "Hello, $it!" }
    }

}