package com.github.infamousriddles.sensecapcontroller.network

import retrofit2.Response
import retrofit2.http.GET

interface SenseCAPService {

    @GET("/sensecap/shutdown")
    suspend fun shutdown(): Response<Unit>

    @GET("/sensecap/reboot")
    suspend fun reboot(): Response<Unit>

    @GET("/sensecap/reset-blocks")
    suspend fun resetblocks(): Response<Unit>
    
    @GET("/sensecap/fast-sync")
    suspend fun fastsync(): Response<Unit>
}
