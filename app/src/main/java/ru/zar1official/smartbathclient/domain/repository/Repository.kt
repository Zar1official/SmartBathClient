package ru.zar1official.smartbathclient.domain.repository

import ru.zar1official.smartbathclient.data.models.BathState

interface Repository {
    suspend fun readBathState(uId: Long): BathState
    suspend fun increaseTemperature()
    suspend fun decreaseTemperature()
    suspend fun readTemperature(): Int
    suspend fun insertLongInPrefs(key: String, long: Long)
    suspend fun readPrefLongValue(key: String): Long?
    suspend fun fetchingWater(uId: Long, fetch: Boolean)
}