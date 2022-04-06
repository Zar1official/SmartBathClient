package ru.zar1official.smartbathclient.domain.repository

import ru.zar1official.smartbathclient.data.models.BathState

interface Repository {
    suspend fun readBathState(uId: Long): BathState
    suspend fun insertLongInPrefs(key: String, long: Long)
    suspend fun readPrefLongValue(key: String): Long?
    suspend fun fetchingWater(uId: Long, fetch: Boolean)
    suspend fun changeWaterColor(uId: Long, color: Int)
    suspend fun changeDrainsStatus(uId: Long, drain: Boolean)
    suspend fun changeTemperature(uId: Long, temperature: Float)
}