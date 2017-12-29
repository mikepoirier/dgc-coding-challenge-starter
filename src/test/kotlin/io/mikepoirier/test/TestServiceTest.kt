package io.mikepoirier.test

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.DynamicTest.dynamicTest
import reactor.test.test

class TestServiceTest {

    lateinit var testService: TestService

    @BeforeEach
    fun setup() {
        testService = TestService()
    }

    @Test
    @DisplayName("returns Hello, World! when no name is given")
    fun getMessageWitNoName() {
        val expected = "Hello, World!"

        val actual = testService.getMessage()

        actual.test()
            .expectNext(expected)
            .verifyComplete()
    }

    @TestFactory
    @DisplayName("when given a name")
    fun getMessagesWithNames(): List<DynamicTest> {
        val data = listOf(
            "Scooby" to "Hello, Scooby!",
            "Shaggy" to "Hello, Shaggy!",
            "Velma" to "Hello, Velma!",
            "Daphne" to "Hello, Daphne!",
            "Fred" to "Hello, Fred!"
        )

        return data.map {
            dynamicTest("return ${it.second} when ${it.first} is given") {
                val actual = testService.getMessage(it.first)

                actual.test()
                    .expectNext(it.second)
                    .verifyComplete()
            }
        }
    }
}