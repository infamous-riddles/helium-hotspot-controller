package com.inframousriddles.plugins

import com.inframousriddles.auth.sensecap.SENSECAP_AUTH_PROVIDER
import com.inframousriddles.sensecap.handlers.SenseCAPHandler
import com.inframousriddles.sensecap.routes.SenseCAPRoutes
import io.ktor.client.*
import io.ktor.client.engine.jetty.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    install(DoubleReceive)

    val client = HttpClient(Jetty)

    routing {
        route("/health") {
            get {
                call.respondText("OK")
            }
        }
        route(SenseCAPRoutes.ROOT) {
            authenticate(SENSECAP_AUTH_PROVIDER) {
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
}
