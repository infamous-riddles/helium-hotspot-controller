package com.inframousriddles.plugins

import com.inframousriddles.sensecap.handlers.SenseCAPHandler
import com.inframousriddles.sensecap.routes.SenseCAPRoutes
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    install(DoubleReceive)

    install(HttpsRedirect) {
        // The port to redirect to. By default, 443, the default HTTPS port.
        sslPort = 443
        // 301 Moved Permanently, or 302 Found redirect.
        permanentRedirect = false
    }

    val client = HttpClient(Jetty)

    routing {
        route("/health") {
            get {
                call.respondText("OK")
            }
        }
        route(SenseCAPRoutes.ROOT) {
            post(path = SenseCAPRoutes.SHUTDOWN_ROUTE) {
                SenseCAPHandler.ShutdownHandler.handle(call = call, client = client)
            }
            post(path = SenseCAPRoutes.REBOOT_ROUTE) {
                SenseCAPHandler.RebootHandler.handle(call = call, client = client)
            }
            post(path = SenseCAPRoutes.RESET_BLOCKS_ROUTE) {
                SenseCAPHandler.ResetBlocksHandler.handle(call = call, client = client)
            }
            post(path = SenseCAPRoutes.FAST_SYNC_ROUTE) {
                SenseCAPHandler.FastSyncHandler.handle(call = call, client = client)
            }
        }
    }
}
