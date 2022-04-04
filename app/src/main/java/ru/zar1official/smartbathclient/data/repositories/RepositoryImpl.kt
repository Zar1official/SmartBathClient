package ru.zar1official.smartbathclient.data.repositories

import ru.zar1official.smartbathclient.data.models.BathState
import ru.zar1official.smartbathclient.data.network.Service
import ru.zar1official.smartbathclient.domain.repository.Repository

class RepositoryImpl(private val service: Service) : Repository {
    override suspend fun readBathState(): BathState = service.readBathState(123)

    override suspend fun increaseTemperature() {

    }

    override suspend fun decreaseTemperature() {

    }

    override suspend fun readTemperature(): Int {
        return listOf(1, 2, 4, 56, 7, 88).random()
    }
}