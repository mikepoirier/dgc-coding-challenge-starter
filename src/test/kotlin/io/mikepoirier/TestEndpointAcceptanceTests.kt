package io.mikepoirier

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import io.mikepoirier.test.TestRequest
import io.mikepoirier.test.TestResponse
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("/test endpoint")
class TestEndpointAcceptanceTests {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    val endpoint = "/test"

    @Nested
    @DisplayName("GET Request")
    inner class WhenGetRequest {
        lateinit var response: ResponseEntity<TestResponse>

        @BeforeEach
        fun beforeEach() {
            response = testRestTemplate.getForEntity(endpoint, TestResponse::class.java)
        }

        @Test
        @DisplayName("returns 200 OK response code")
        fun returnsOkStatus() {
            assert.that(response.statusCode, equalTo(HttpStatus.OK))
        }

        @Test
        @DisplayName("returns TestResponse")
        fun returnProperBody() {
            val bodyMatcher = has(TestResponse::message, equalTo("Hello, World!"))

            assert.that(response.body!!, bodyMatcher)
        }
    }

    @Nested
    @DisplayName("POST Request")
    inner class WhenPostRequest {

        private val data = listOf(
            RequestResponseTest("Hello, World!", TestRequest("World"), HttpStatus.OK, TestResponse("Hello, World!")),
            RequestResponseTest("Hello, You!", TestRequest("You"), HttpStatus.OK, TestResponse("Hello, You!")),
            RequestResponseTest("Hello, Mike!", TestRequest("Mike"), HttpStatus.OK, TestResponse("Hello, Mike!"))
        )

        @TestFactory
        fun tests(): List<DynamicTest> {
            return data.map { it.toDynamicTest<TestResponse>(endpoint, testRestTemplate) }
        }
    }
}