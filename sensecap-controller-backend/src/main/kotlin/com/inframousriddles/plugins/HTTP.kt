package com.inframousriddles.plugins

import com.inframousriddles.auth.sensecap.SENSECAP_AUTH_PROVIDER
import com.inframousriddles.auth.sensecap.SenseCAPAuthCreds
import com.inframousriddles.auth.sensecap.fromEnv
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(Authentication) {
        basic(SENSECAP_AUTH_PROVIDER) {
            realm = "Access to SenseCAP Routes"
            validate { credentials ->
                val senseCAPUsername = SenseCAPAuthCreds.SENSECAP_USERNAME.fromEnv
                val senseCAPPassword = SenseCAPAuthCreds.SENSECAP_PASSWORD.fromEnv

                if (credentials.name == senseCAPUsername && credentials.password == senseCAPPassword) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}
