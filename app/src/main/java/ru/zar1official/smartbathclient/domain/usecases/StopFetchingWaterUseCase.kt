package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class StopFetchingWaterUseCase(private val repository: Repository) {
    suspend fun invoke(uId: Long) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            repository.fetchingWater(uId, false)
        }.onFailure {
            return@withContext PostRequestResult.Error
        }
        return@withContext PostRequestResult.Success
    }
}