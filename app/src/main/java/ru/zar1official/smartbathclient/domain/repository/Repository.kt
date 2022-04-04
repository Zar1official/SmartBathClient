package ru.zar1official.smartbathclient.domain.repository

interface Repository {
    suspend fun increaseTemperature()
    suspend fun decreaseTemperature()
    suspend fun readTemperature(): Int
}