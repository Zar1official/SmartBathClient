package ru.zar1official.smartbathclient.data.network

import ru.zar1official.smartbathclient.data.models.BathState

interface Service {
    suspend fun readBathState(uId: Long): BathState
    suspend fun fetchingWater(uId: Long, fetch: Boolean)
}