package io.mikepoirier

import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class RequestResponseTest(
    val name: String,
    val request: Any,
    val code: HttpStatus,
    val response: Any
) {
    inline fun <reified U: Any> toDynamicTest(endpoint: String, testRestTemplate: TestRestTemplate): DynamicTest {
        return dynamicTest(name) {
            val actual: ResponseEntity<U> = testRestTemplate.postForEntity(endpoint, request)

            val hasCorrectStatus = has(ResponseEntity<U>::getStatusCode, equalTo(code))
            val hasCorrectBody = has(ResponseEntity<U>::getBody, equalTo(response))

            assert.that(actual, hasCorrectStatus and hasCorrectBody)
        }
    }
}