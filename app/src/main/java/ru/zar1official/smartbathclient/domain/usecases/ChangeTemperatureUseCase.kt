package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class ChangeTemperatureUseCase(private val repository: Repository) {
    suspend operator fun invoke(uId: Long, temperature: Float): PostRequestResult =
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                repository.changeTemperature(
                    uId = uId,
                    temperature = temperature
                )
            }.onFailure {
                return@withContext PostRequestResult.Error
            }
            return@withContext PostRequestResult.Success
        }
}