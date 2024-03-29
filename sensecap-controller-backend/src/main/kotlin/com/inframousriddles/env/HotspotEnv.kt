package com.inframousriddles.env

enum class HotspotEnv(val actualName: String) {
  HOTSPOT_API(actualName = "HOTSPOT_API"),
  HOTSPOT_TOKEN(actualName = "HOTSPOT_TOKEN")
}

val HotspotEnv.fromEnv
  get() = System.getenv(this.actualName) ?: throw IllegalStateException("${this.actualName} is not in current env!")
