package com.inframousriddles.sensecap.handlers

import com.inframousriddles.env.HotspotEnv
import com.inframousriddles.env.fromEnv
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.server.application.*

interface SenseCAPHandler {
  suspend fun handle(call: ApplicationCall, client: HttpClient)

  object ShutdownHandler : SenseCAPHandler {
    override suspend fun handle(call: ApplicationCall, client: HttpClient) {
      internalHandle(call = call, client = client, hotspotAction = HotspotAction.SHUTDOWN)
    }
  }

  object RebootHandler : SenseCAPHandler {
    override suspend fun handle(call: ApplicationCall, client: HttpClient) {
      internalHandle(call = call, client = client, hotspotAction = HotspotAction.REBOOT)
    }
  }

  object ResetBlocksHandler : SenseCAPHandler {
    override suspend fun handle(call: ApplicationCall, client: HttpClient) {
      internalHandle(call = call, client = client, hotspotAction = HotspotAction.RESET_BLOCKS)
    }
  }

  object FastSyncHandler : SenseCAPHandler {
    override suspend fun handle(call: ApplicationCall, client: HttpClient) {
      internalHandle(call = call, client = client, hotspotAction = HotspotAction.FAST_SYNC)
    }
  }

  suspend fun internalHandle(
    call: ApplicationCall,
    client: HttpClient,
    hotspotAction: HotspotAction
  ) {
    val logger = call.logger
    val hotspotActionAPI = when(hotspotAction) {
      HotspotAction.SHUTDOWN -> "shutdown"
      HotspotAction.REBOOT -> "reboot"
      HotspotAction.RESET_BLOCKS -> "resetblocks"
      HotspotAction.FAST_SYNC -> "turbosync"
    }
    val hotspotActionEndpoint = "${HotspotEnv.HOTSPOT_API.fromEnv}/$hotspotActionAPI"
    val hotspotActionName = hotspotAction.name
    logger.info(
      """
        Received $hotspotActionName request! Going to invoke $hotspotActionName on endpoint: $hotspotActionEndpoint 
      """.trimIndent()
    )

    val hotspotActionResponse = client.post(urlString = hotspotActionEndpoint)

    logger.info(
      """
       $hotspotActionName received status code: ${hotspotActionResponse.call.response.status}
      """.trimIndent()
    )
  }

  val ApplicationCall.logger
    get() = this.application.environment.log
}
