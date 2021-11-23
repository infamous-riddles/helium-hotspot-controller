package com.inframousriddles

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.inframousriddles.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureHTTP()
    }.start(wait = true)
}
