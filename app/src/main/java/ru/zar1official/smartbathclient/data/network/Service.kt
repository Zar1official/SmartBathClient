package ru.zar1official.smartbathclient.data.network

import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @POST
    suspend fun increaseTemperature()

    @POST
    suspend fun decreaseTemperature()

    @GET
    suspend fun readTemperature()
}