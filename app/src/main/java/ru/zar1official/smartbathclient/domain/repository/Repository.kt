package ru.zar1official.smartbathclient.domain.repository

import ru.zar1official.smartbathclient.data.models.BathState

interface Repository {
    suspend fun readBathState(): BathState
    suspend fun increaseTemperature()
    suspend fun decreaseTemperature()
    suspend fun readTemperature(): Int
}