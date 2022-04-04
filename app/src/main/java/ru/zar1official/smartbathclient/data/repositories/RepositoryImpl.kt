package ru.zar1official.smartbathclient.data.repositories

import ru.zar1official.smartbathclient.domain.repository.Repository

class RepositoryImpl : Repository {
    override suspend fun increaseTemperature() {

    }

    override suspend fun decreaseTemperature() {

    }

    override suspend fun readTemperature(): Int {
        return listOf(1, 2, 4, 56, 7, 88).random()
    }
}