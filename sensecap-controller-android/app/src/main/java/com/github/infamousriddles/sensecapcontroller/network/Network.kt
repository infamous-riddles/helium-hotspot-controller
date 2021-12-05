package com.github.infamousriddles.sensecapcontroller.network

import com.github.infamousriddles.sensecapcontroller.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://${BuildConfig.SENSECAP_HOST}/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun senseCapService(): SenseCAPService = retrofit.create()
}
