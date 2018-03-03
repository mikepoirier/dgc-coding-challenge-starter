package io.mikepoirier

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}
