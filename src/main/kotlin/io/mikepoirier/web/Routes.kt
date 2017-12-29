package io.mikepoirier.web

import io.mikepoirier.test.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

@Configuration
class Routes(
    val testHandler: TestHandler,
    val errorHandler: ErrorHandler,
    val challengeHandler: ChallengeHandler
) {

    @Bean
    fun apiRoutes() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "/path".nest {
                GET("/", { Mono.empty() })
            }
            "/test".nest {
                GET("/", testHandler::handleGet)
                POST("/", testHandler::handlePost)
            }
            "/challenge".nest {
                GET("/", challengeHandler::handleGet)
                POST("/", challengeHandler::handlePost)
                PUT("/", challengeHandler::handlePut)
                PATCH("/", challengeHandler::handlePatch)
                DELETE("/", challengeHandler::handleDelete)
            }
            "/error".nest {
                GET("/", errorHandler::handleGet)
            }
        }
    }
}