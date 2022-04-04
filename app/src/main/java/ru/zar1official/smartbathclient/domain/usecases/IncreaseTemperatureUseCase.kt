package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.Constants
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class IncreaseTemperatureUseCase(private val repository: Repository) {
    suspend operator fun invoke(temperature: Int): PostRequestResult = withContext(Dispatchers.IO) {
        if (temperature < Constants.TEMPERATURE_MAX_VALUE) {
            kotlin.runCatching {
                repository.increaseTemperature()
            }.onSuccess {
                return@withContext PostRequestResult.Success
            }.onFailure {
                return@withContext PostRequestResult.NetworkError
            }
        }
        return@withContext PostRequestResult.DataError
    }
}