package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.GetRequestResult

class ReadTemperatureUseCase(private val repository: Repository) {
    suspend operator fun invoke(): GetRequestResult<Int> =
        withContext(Dispatchers.IO) {
            val data = kotlin.runCatching { repository.readTemperature() }.getOrNull()
                ?: return@withContext GetRequestResult.NetworkError
            return@withContext GetRequestResult.Success(data)
        }
}