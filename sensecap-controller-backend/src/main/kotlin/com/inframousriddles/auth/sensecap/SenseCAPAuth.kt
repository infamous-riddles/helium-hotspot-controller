package com.inframousriddles.auth.sensecap

enum class SenseCAPAuthCreds(val actualName: String) {
  SENSECAP_USERNAME(actualName = "SENSECAP_USERNAME"),
  SENSECAP_PASSWORD(actualName = "SENSECAP_PASSWORD")
}

val SenseCAPAuthCreds.fromEnv
  get() = System.getenv(this.actualName) ?: throw IllegalStateException("${this.actualName} is not in current env!")

const val SENSECAP_AUTH_PROVIDER = "sensecap"
